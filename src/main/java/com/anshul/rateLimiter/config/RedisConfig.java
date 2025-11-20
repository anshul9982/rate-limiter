package com.anshul.rateLimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // KEY SERIALIZER: Ensures keys are readable strings
        template.setKeySerializer(new StringRedisSerializer());

        // VALUE SERIALIZER: Ensures values are stored as numbers/strings
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));

        return template;
    }
}
