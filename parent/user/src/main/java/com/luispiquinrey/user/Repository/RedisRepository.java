package com.luispiquinrey.user.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Entities.RedisContact;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RedisContact, Long>{

    public void save(Contact contact);
    
}
