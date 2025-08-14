package com.app.gateway.ratelimit;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Token Bucket algorithm implementation for rate limiting.
 * Thread-safe implementation using atomic operations.
 */
public class TokenBucket {
    private final long capacity;
    private final long refillRate; // tokens per second
    private final AtomicLong tokens;
    private volatile long lastRefillTime;

    /**
     * Creates a new TokenBucket with specified capacity and refill rate.
     *
     * @param capacity    Maximum number of tokens the bucket can hold
     * @param refillRate  Number of tokens added per second
     */
    public TokenBucket(long capacity, long refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = new AtomicLong(capacity);
        this.lastRefillTime = System.currentTimeMillis();
    }

    /**
     * Attempts to consume one token from the bucket.
     *
     * @return true if a token was successfully consumed, false if no tokens available
     */
    public synchronized boolean allowRequest() {
        refill();
        long currentTokens = tokens.get();
        
        if (currentTokens > 0) {
            tokens.decrementAndGet();
            return true;
        }
        
        return false;
    }

    /**
     * Attempts to consume the specified number of tokens from the bucket.
     *
     * @param tokensRequested Number of tokens to consume
     * @return true if the tokens were successfully consumed, false otherwise
     */
    public synchronized boolean allowRequest(long tokensRequested) {
        if (tokensRequested <= 0) {
            return true;
        }
        
        refill();
        long currentTokens = tokens.get();
        
        if (currentTokens >= tokensRequested) {
            tokens.addAndGet(-tokensRequested);
            return true;
        }
        
        return false;
    }

    /**
     * Refills the bucket based on the time elapsed since the last refill.
     */
    private void refill() {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastRefillTime;
        
        if (timePassed > 0) {
            long tokensToAdd = (timePassed * refillRate) / 1000; // Convert milliseconds to seconds
            
            if (tokensToAdd > 0) {
                long currentTokens = tokens.get();
                long newTokens = Math.min(capacity, currentTokens + tokensToAdd);
                tokens.set(newTokens);
                lastRefillTime = currentTime;
            }
        }
    }

    /**
     * Gets the current number of tokens in the bucket.
     *
     * @return Current token count
     */
    public long getAvailableTokens() {
        refill();
        return tokens.get();
    }

    /**
     * Gets the bucket capacity.
     *
     * @return Maximum number of tokens the bucket can hold
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Gets the refill rate.
     *
     * @return Number of tokens added per second
     */
    public long getRefillRate() {
        return refillRate;
    }
}
