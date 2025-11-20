package com.anshul.rateLimiter.service;

import com.anshul.rateLimiter.strategy.RateLimiterStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class RateLimiterService {
    private final RateLimiterStrategy strategy;
    private final RateLimitConfigService config;



    public RateLimiterService(@Qualifier("tokenBucket") RateLimiterStrategy strategy, RateLimitConfigService config){
        this.strategy = strategy;
        this.config = config;
    }

    public Mono<Boolean> isAllowed(String key){
        return Mono.fromCallable(()->config.getConfig(key)).subscribeOn(Schedulers.boundedElastic())
                .flatMap(config->{
                    String redisKey = "rate_limit:"+key;
                    return strategy.isAllowed(redisKey, config.getLimit(), config.getWindowSeconds());
                });
    }
}
