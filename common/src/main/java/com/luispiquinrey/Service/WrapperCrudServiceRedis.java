package com.luispiquinrey.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;

import com.luispiquinrey.Entities.BaseEntity;
import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.SearchException;
import com.luispiquinrey.Error.UpdateException;

public class WrapperCrudServiceRedis<T extends BaseEntity<ID>, ID> extends CrudService<T, ID> {

    public final RedisTemplate<String,T> redisTemplate;
    private String CACHE_PREFIX;

    @Autowired
    public WrapperCrudServiceRedis(RedisTemplate<String,T> redisTemplate,CrudRepository<T, ID> repositoryGeneric, Class<T> entityClass) {
        super(repositoryGeneric, entityClass);
        this.redisTemplate=redisTemplate;
        this.CACHE_PREFIX = entityClass.getSimpleName().toLowerCase() + ":id:";
    }

    @Override
    public T updateTarget(T target) throws UpdateException {
        T updated= super.updateTarget(target);
        redisTemplate.opsForValue().set(CACHE_PREFIX+updated.getId(),target);
        return updated;
    }

    @Override
    public T createTarget(T target) throws CreationException {
        T created= super.createTarget(target);
        redisTemplate.opsForValue().set(CACHE_PREFIX+created.getId(),target);
        return created;
    }

    @Override
    public void deleteTarget(ID idTarget) throws DeleteException {
        super.deleteTarget(idTarget);
        redisTemplate.delete(CACHE_PREFIX+idTarget);
    }

    @Override
    public Optional<T> findTargetById(ID idTarget) throws SearchException {
        T cached = redisTemplate.opsForValue().get(CACHE_PREFIX+idTarget);
        if (cached != null) {
            return Optional.of(cached);
        }
        Optional<T> result = super.findTargetById(idTarget);
        result.ifPresent(entity -> 
            redisTemplate.opsForValue().set(CACHE_PREFIX+idTarget, entity)
        );
        
        return result;
    }
    
}