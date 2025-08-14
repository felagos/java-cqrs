# Gateway Rate Limiting Implementation

This implementation provides Token Bucket rate limiting for the Spring Cloud Gateway service.

## Overview

The rate limiting system uses the **Token Bucket Algorithm** to control the rate of incoming requests:

- Each client (identified by IP address) gets their own token bucket
- Buckets have a maximum capacity and refill at a configurable rate
- Requests consume tokens; if no tokens are available, the request is rejected with HTTP 429 (Too Many Requests)

## Components

### 1. TokenBucket (`com.app.gateway.ratelimit.TokenBucket`)

Core implementation of the Token Bucket algorithm:
- **Thread-safe** using atomic operations and synchronization
- **Configurable capacity** and refill rate
- **Automatic refill** based on elapsed time
- **Burst handling** - allows consuming multiple tokens at once

### 2. RateLimitingFilter (`com.app.gateway.filters.RateLimitingFilter`)

Spring Cloud Gateway Global Filter that:
- Applies rate limiting to all incoming requests
- Maintains per-client token buckets
- Adds standard rate limiting headers to responses
- Periodically cleans up inactive buckets to prevent memory leaks

### 3. RateLimitProperties (`com.app.gateway.config.RateLimitProperties`)

Configuration properties for customizing rate limiting behavior:
- `app.ratelimit.enabled` - Enable/disable rate limiting
- `app.ratelimit.capacity` - Maximum tokens per bucket
- `app.ratelimit.refill-rate` - Tokens added per second
- `app.ratelimit.cleanup-interval` - Cleanup interval in seconds

## Configuration

Add these properties to `application.properties`:

```properties
# Rate Limiting Configuration
app.ratelimit.enabled=true
app.ratelimit.capacity=100
app.ratelimit.refill-rate=10
app.ratelimit.cleanup-interval=300
```

### Configuration Parameters

- **capacity** (default: 100): Maximum number of tokens a bucket can hold
- **refill-rate** (default: 10): Number of tokens added to the bucket per second
- **cleanup-interval** (default: 300): How often (in seconds) to clean up inactive buckets
- **enabled** (default: true): Whether rate limiting is active

## Rate Limiting Headers

The gateway adds these headers to responses:

- `X-RateLimit-Limit`: Maximum requests allowed
- `X-RateLimit-Remaining`: Number of tokens remaining
- `X-RateLimit-Reset`: When the rate limit window resets
- `Retry-After`: How long to wait before retrying (on 429 responses)

## Client Identification

Clients are identified by IP address using this priority:
1. `X-Forwarded-For` header (first IP if multiple)
2. `X-Real-IP` header
3. Remote address from the request

## Example Usage

### Normal Request Flow
```
GET /products/1
< 200 OK
< X-RateLimit-Limit: 100
< X-RateLimit-Remaining: 99
< X-RateLimit-Reset: 1692123456
```

### Rate Limited Request
```
GET /products/1
< 429 Too Many Requests
< X-RateLimit-Limit: 100
< X-RateLimit-Remaining: 0
< X-RateLimit-Reset: 1692123456
< Retry-After: 60
```

## Testing

Run the unit tests to verify the Token Bucket algorithm:

```bash
./gradlew :gateway:test
```

## Performance Considerations

- **Memory Usage**: Buckets are stored per client IP. Inactive buckets are cleaned up periodically.
- **Thread Safety**: Uses atomic operations and minimal synchronization for high performance.
- **Scalability**: Suitable for moderate traffic; for high scale consider Redis-based rate limiting.

## Monitoring

The rate limiting filter can be monitored through:
- Spring Boot Actuator endpoints
- Application logs
- Custom metrics (can be added)

## Customization

To customize the rate limiting behavior:

1. **Different rate limits per endpoint**: Extend the filter to check request paths
2. **User-based rate limiting**: Modify client identification to use user ID instead of IP
3. **Different algorithms**: Replace TokenBucket with other algorithms (Sliding Window, Fixed Window)
4. **Distributed rate limiting**: Use Redis or other external storage for bucket state

## Integration with Discovery

The rate limiting works seamlessly with:
- Eureka service discovery
- Load balancing
- Circuit breakers
- Other Spring Cloud Gateway filters
