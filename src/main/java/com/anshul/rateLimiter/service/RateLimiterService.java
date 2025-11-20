package com.anshul.rateLimiter.service;

import com.anshul.rateLimiter.strategy.RateLimiterStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RateLimiterService {
    private final RateLimiterStrategy strategy;

    public RateLimiterService(@Qualifier("fixedWindow") RateLimiterStrategy strategy){
        this.strategy = strategy;
    }

    public Boolean isAllowed(String key, int limit, int windowSeconds){
        String redisKey = "rate_limit:" + key;
        return strategy.isAllowed(redisKey, limit, windowSeconds);
    }
}
