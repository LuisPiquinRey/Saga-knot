package com.luispiquinrey.user.Service;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.UpdateException;
import com.luispiquinrey.Service.WrapperCrudServiceRedis;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Repository.ContactRepository;

@Service
public class ContactService extends WrapperCrudServiceRedis<Contact, Long> {

    private final ContactRepository contactRepository;
    private static final String USERNAME_CACHE_PREFIX = "contact:username:";

    public ContactService(RedisTemplate<String, Contact> redisTemplate,
                        ContactRepository contactRepository) {
        super(redisTemplate, contactRepository, Contact.class);
        this.contactRepository = contactRepository;
    }

    public Optional<Contact> findByUsername(String username) {
        String cacheKey = USERNAME_CACHE_PREFIX + username;
        Contact cached = super.redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) return Optional.of(cached);

        Optional<Contact> contact = contactRepository.findByUsername(username);
        contact.ifPresent(c -> super.redisTemplate.opsForValue().set(cacheKey, c));
        return contact;
    }

    @Override
    public Contact createTarget(Contact target) throws CreationException {
        Contact created = super.createTarget(target);
        super.redisTemplate.opsForValue().set(USERNAME_CACHE_PREFIX + created.getUsername(), created);
        return created;
    }

    @Override
    public Contact updateTarget(Contact target) throws UpdateException {
        Contact updated = super.updateTarget(target);
        super.redisTemplate.opsForValue().set(USERNAME_CACHE_PREFIX + updated.getUsername(), updated);
        return updated;
    }

    @Override
    public void deleteTarget(Long id) throws DeleteException {
        Optional<Contact> contactOpt = super.findTargetById(id);
        super.deleteTarget(id);
        contactOpt.ifPresent(c -> super.redisTemplate.delete(USERNAME_CACHE_PREFIX + c.getUsername()));
    }
}
