package com.app.gateway.filters;

import com.app.gateway.config.RateLimitProperties;
import com.app.gateway.ratelimit.TokenBucket;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Global filter that applies rate limiting to all requests passing through the gateway.
 * Uses Token Bucket algorithm with per-client rate limiting based on IP address.
 */
@Component
public class RateLimitingFilter implements GlobalFilter, Ordered {
    
    private final ConcurrentHashMap<String, TokenBucket> clientBuckets = new ConcurrentHashMap<>();
    private final RateLimitProperties rateLimitProperties;
    private volatile long lastCleanup = System.currentTimeMillis();
    
    public RateLimitingFilter(RateLimitProperties rateLimitProperties) {
        this.rateLimitProperties = rateLimitProperties;
    }
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Skip rate limiting if disabled
        if (!rateLimitProperties.isEnabled()) {
            return chain.filter(exchange);
        }
        
        String clientId = getClientId(exchange);
        
        // Periodic cleanup of inactive buckets
        cleanupInactiveBuckets();
        
        // Get or create bucket for this client
        @SuppressWarnings("unused")
        TokenBucket bucket = clientBuckets.computeIfAbsent(clientId, 
            k -> new TokenBucket(rateLimitProperties.getCapacity(), rateLimitProperties.getRefillRate()));
        
        // Check if request is allowed
        if (!bucket.allowRequest()) {
            return handleRateLimitExceeded(exchange);
        }
        
        // Add rate limiting headers to response
        addRateLimitHeaders(exchange, bucket);
        
        return chain.filter(exchange);
    }
    
    /**
     * Extracts client identifier from the request.
     * Uses IP address as the default identifier.
     */
    private String getClientId(ServerWebExchange exchange) {
        // Try to get real IP from X-Forwarded-For header (for load balancers/proxies)
        String xForwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        // Try X-Real-IP header
        String xRealIp = exchange.getRequest().getHeaders().getFirst("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        // Fall back to remote address
        return exchange.getRequest().getRemoteAddress() != null ?
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() : "unknown";
    }
    
    /**
     * Handles rate limit exceeded scenario.
     */
    private Mono<Void> handleRateLimitExceeded(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        
        // Add standard rate limiting headers
        response.getHeaders().add("X-RateLimit-Limit", String.valueOf(rateLimitProperties.getCapacity()));
        response.getHeaders().add("X-RateLimit-Remaining", "0");
        response.getHeaders().add("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + 60));
        response.getHeaders().add("Retry-After", "60");
        
        return response.setComplete();
    }
    
    /**
     * Adds rate limiting information to response headers.
     */
    private void addRateLimitHeaders(ServerWebExchange exchange, TokenBucket bucket) {
        ServerHttpResponse response = exchange.getResponse();
        
        response.getHeaders().add("X-RateLimit-Limit", String.valueOf(rateLimitProperties.getCapacity()));
        response.getHeaders().add("X-RateLimit-Remaining", String.valueOf(bucket.getAvailableTokens()));
        response.getHeaders().add("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + 60));
    }
    
    /**
     * Periodically cleans up inactive buckets to prevent memory leaks.
     */
    private void cleanupInactiveBuckets() {
        long currentTime = System.currentTimeMillis();
        long cleanupIntervalMs = rateLimitProperties.getCleanupInterval() * 1000; // Convert seconds to milliseconds
        
        if (currentTime - lastCleanup > cleanupIntervalMs) {
            // Remove buckets that are at full capacity (indicating no recent activity)
            clientBuckets.entrySet().removeIf(entry -> 
                entry.getValue().getAvailableTokens() == rateLimitProperties.getCapacity());
            
            lastCleanup = currentTime;
        }
    }
    
    @Override
    public int getOrder() {
        return -1; // High priority - execute before other filters
    }
}
