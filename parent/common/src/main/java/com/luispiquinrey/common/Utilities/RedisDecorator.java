package com.luispiquinrey.common.Utilities;

import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;

import com.luispiquinrey.common.Entities.BaseEntity;
import com.luispiquinrey.common.Error.CreationException;
import com.luispiquinrey.common.Error.DeleteException;
import com.luispiquinrey.common.Error.SearchException;
import com.luispiquinrey.common.Error.UpdateException;

public class RedisDecorator<T extends BaseEntity<ID>, ID> extends BaseDecorator<T, ID> {

    private final RedisTemplate<String, T> redisTemplate;
    private final String cachePrefix;

    public RedisDecorator(IDataService<T, ID> delegate,
                          RedisTemplate<String, T> redisTemplate,
                          Class<T> entityClass) {
        super(delegate);
        this.redisTemplate = redisTemplate;
        this.cachePrefix = entityClass.getSimpleName().toLowerCase() + ":id:";
    }

    @Override
    public Optional<T> findTargetById(ID idTarget) throws SearchException {
        T cached = redisTemplate.opsForValue().get(cachePrefix + idTarget);
        if (cached != null) return Optional.of(cached);

        Optional<T> result = super.findTargetById(idTarget);
        result.ifPresent(entity -> redisTemplate.opsForValue().set(cachePrefix + idTarget, entity));
        return result;
    }

    @Override
    public T createTarget(T target) throws CreationException {
        T created = super.createTarget(target);
        redisTemplate.opsForValue().set(cachePrefix + created.getId(), created);
        return created;
    }

    @Override
    public T updateTarget(T target) throws UpdateException {
        T updated = super.updateTarget(target);
        redisTemplate.opsForValue().set(cachePrefix + updated.getId(), updated);
        return updated;
    }

    @Override
    public void deleteTarget(ID idTarget) throws DeleteException {
        super.deleteTarget(idTarget);
        redisTemplate.delete(cachePrefix + idTarget);
    }
}
