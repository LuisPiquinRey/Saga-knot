package com.luispiquinrey.user.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.luispiquinrey.user.Entities.RedisContact;

@NoRepositoryBean
public interface RedisRepository extends CrudRepository<RedisContact, Long>{
    
}
