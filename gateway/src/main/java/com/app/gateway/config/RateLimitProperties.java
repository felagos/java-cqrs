package com.app.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for rate limiting.
 */
@Configuration
@ConfigurationProperties(prefix = "app.ratelimit")
public class RateLimitProperties {
    
    private int capacity = 100;           // Default: 100 requests per bucket
    private int refillRate = 10;          // Default: 10 requests per second
    private long cleanupInterval = 300;   // Default: 5 minutes
    private boolean enabled = true;       // Default: rate limiting enabled
    
    // Getters and setters
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getRefillRate() {
        return refillRate;
    }
    
    public void setRefillRate(int refillRate) {
        this.refillRate = refillRate;
    }
    
    public long getCleanupInterval() {
        return cleanupInterval;
    }
    
    public void setCleanupInterval(long cleanupInterval) {
        this.cleanupInterval = cleanupInterval;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
