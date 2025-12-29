package com.luispiquinrey.product.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luispiquinrey.product.Entities.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Configuration
    public class ProductRedisConfig {

        @Bean
        public RedisTemplate<String, Product> productRedisTemplate(RedisConnectionFactory connectionFactory,
                                                                   ObjectMapper objectMapper) {
            Jackson2JsonRedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(Product.class);
            RedisTemplate<String, Product> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(serializer);
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(serializer);
            template.afterPropertiesSet();
            return template;
        }
    }
}
