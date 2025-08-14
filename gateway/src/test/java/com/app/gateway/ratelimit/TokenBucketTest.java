package com.app.gateway.ratelimit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TokenBucket rate limiting algorithm.
 */
class TokenBucketTest {

    private TokenBucket tokenBucket;

    @BeforeEach
    void setUp() {
        // Create a bucket with capacity 5 and refill rate 2 tokens/second
        tokenBucket = new TokenBucket(5, 2);
    }

    @Test
    void testInitialTokens() {
        // Should start with full capacity
        assertEquals(5, tokenBucket.getAvailableTokens());
        assertEquals(5, tokenBucket.getCapacity());
        assertEquals(2, tokenBucket.getRefillRate());
    }

    @Test
    void testAllowRequest() {
        // Should allow requests until bucket is empty
        assertTrue(tokenBucket.allowRequest());
        assertEquals(4, tokenBucket.getAvailableTokens());
        
        assertTrue(tokenBucket.allowRequest());
        assertTrue(tokenBucket.allowRequest());
        assertTrue(tokenBucket.allowRequest());
        assertTrue(tokenBucket.allowRequest());
        
        // Now bucket should be empty
        assertEquals(0, tokenBucket.getAvailableTokens());
        assertFalse(tokenBucket.allowRequest());
    }

    @Test
    void testAllowMultipleTokens() {
        // Test consuming multiple tokens at once
        assertTrue(tokenBucket.allowRequest(3));
        assertEquals(2, tokenBucket.getAvailableTokens());
        
        // Should fail if requesting more tokens than available
        assertFalse(tokenBucket.allowRequest(3));
        assertEquals(2, tokenBucket.getAvailableTokens());
        
        // Should succeed for available tokens
        assertTrue(tokenBucket.allowRequest(2));
        assertEquals(0, tokenBucket.getAvailableTokens());
    }

    @Test
    void testRefill() throws InterruptedException {
        // Empty the bucket
        for (int i = 0; i < 5; i++) {
            tokenBucket.allowRequest();
        }
        assertEquals(0, tokenBucket.getAvailableTokens());
        
        // Wait for refill (1 second should add 2 tokens)
        Thread.sleep(1100); // Wait a bit more than 1 second to ensure refill
        
        // Should have refilled some tokens
        assertTrue(tokenBucket.getAvailableTokens() >= 2);
        assertTrue(tokenBucket.allowRequest());
    }

    @Test
    void testRefillDoesNotExceedCapacity() throws InterruptedException {
        // Wait for longer than needed to fill the bucket
        Thread.sleep(3000); // 3 seconds should try to add 6 tokens
        
        // Should not exceed capacity
        assertEquals(5, tokenBucket.getAvailableTokens());
    }

    @Test
    void testZeroTokensRequest() {
        // Requesting 0 tokens should always succeed
        assertTrue(tokenBucket.allowRequest(0));
        assertEquals(5, tokenBucket.getAvailableTokens());
    }

    @Test
    void testNegativeTokensRequest() {
        // Requesting negative tokens should always succeed
        assertTrue(tokenBucket.allowRequest(-1));
        assertEquals(5, tokenBucket.getAvailableTokens());
    }
}
