package com.luispiquinrey.user.Entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@RedisHash(value = "contact", timeToLive = 3600)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@Builder
public class RedisContact implements Serializable {

    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    private String idContact; 

    @ToString.Include
    @Indexed
    private String username;

    @ToString.Include
    @Indexed
    private String email;

    @ToString.Include
    private String phoneNumber;
}
