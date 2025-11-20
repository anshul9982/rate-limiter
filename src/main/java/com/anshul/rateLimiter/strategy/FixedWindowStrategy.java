package com.anshul.rateLimiter.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component("fixedWindow")
@RequiredArgsConstructor
public class FixedWindowStrategy implements RateLimiterStrategy {

    private final RedisTemplate<String, Object> redisTemplate;
    @Override
    public boolean isAllowed(String key, int limit, int windowSeconds){
        Long count = redisTemplate.opsForValue().increment(key);
        if(count != null && count == 1){
            redisTemplate.expire(key, Duration.ofSeconds(windowSeconds));
        }

        return count != null && count<=limit;
    }


}
