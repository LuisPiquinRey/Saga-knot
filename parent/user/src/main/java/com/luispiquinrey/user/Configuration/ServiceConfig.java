package com.luispiquinrey.user.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.luispiquinrey.Service.FacadeServiceWithRedis;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Repository.ContactRepository;

@Configuration
public class ServiceConfig {

    private final RedisTemplate redisTemplate; 

    @Autowired
    public ServiceConfig(RedisTemplate redisTemplate) {
        this.redisTemplate =redisTemplate;
    }

    @Bean
    public FacadeServiceWithRedis<Contact, Long> contactFacadeService(
            RedisTemplate redisTemplate,
            ContactRepository contactRepository) {
        return new FacadeServiceWithRedis<>(redisTemplate, contactRepository, Contact.class);
    }
}