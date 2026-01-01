package com.luispiquinrey.common.Utilities;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.luispiquinrey.common.Entities.BaseEntity;
import com.luispiquinrey.common.Error.CreationException;
import com.luispiquinrey.common.Error.DeleteException;
import com.luispiquinrey.common.Error.SearchException;
import com.luispiquinrey.common.Error.UpdateException;
import org.springframework.stereotype.Component;

public class RedisDecorator<T extends BaseEntity<ID>, ID> extends BaseDecorator<T, ID> {

    private static final Logger log = LoggerFactory.getLogger(RedisDecorator.class);

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
        String key = cachePrefix + idTarget;
        try {
            T cached = redisTemplate.opsForValue().get(key);
            if (cached != null) {
                log.info("✅ Cache hit for key {}", key);
                return Optional.of(cached);
            } else {
                log.info("⚠️ Cache miss for key {}", key);
            }
        } catch (Exception e) {
            log.error("❌ Error reading from Redis for key {}", key, e);
        }

        Optional<T> result = super.findTargetById(idTarget);
        result.ifPresent(entity -> {
            try {
                redisTemplate.opsForValue().set(key, entity);
                log.info("🆕 Saved entity to Redis with key {}", key);
            } catch (Exception e) {
                log.error("❌ Failed to save entity to Redis with key {}", key, e);
            }
        });
        return result;
    }

    @Override
    public T createTarget(T target) throws CreationException {
        T created = super.createTarget(target);
        String key = cachePrefix + created.getId();
        try {
            redisTemplate.opsForValue().set(key, created);
            log.info("🆕 Created entity in Redis with key {}", key);
        } catch (Exception e) {
            log.error("❌ Failed to create entity in Redis with key {}", key, e);
        }
        return created;
    }

    @Override
    public T updateTarget(T target) throws UpdateException {
        T updated = super.updateTarget(target);
        String key = cachePrefix + updated.getId();
        try {
            redisTemplate.opsForValue().set(key, updated);
            log.info("✏️ Updated entity in Redis with key {}", key);
        } catch (Exception e) {
            log.error("❌ Failed to update entity in Redis with key {}", key, e);
        }
        return updated;
    }

    @Override
    public void deleteTarget(ID idTarget) throws DeleteException {
        super.deleteTarget(idTarget);
        String key = cachePrefix + idTarget;
        try {
            redisTemplate.delete(key);
            log.info("🗑️ Deleted entity from Redis with key {}", key);
        } catch (Exception e) {
            log.error("❌ Failed to delete entity from Redis with key {}", key, e);
        }
    }
}