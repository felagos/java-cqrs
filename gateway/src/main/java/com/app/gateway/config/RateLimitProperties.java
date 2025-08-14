package com.app.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for rate limiting.
 */
@Configuration
@ConfigurationProperties(prefix = "app.ratelimit")
public class RateLimitProperties {

    private int capacity = 100;
    private int refillRate = 10;
    private long cleanupInterval = 300;
    private boolean enabled = true;

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
