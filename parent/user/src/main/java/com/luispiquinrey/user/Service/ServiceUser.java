package com.luispiquinrey.user.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.Error.CreationException;
import com.luispiquinrey.Error.DeleteException;
import com.luispiquinrey.Error.SearchException;
import com.luispiquinrey.Error.UpdateException;
import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Entities.RedisContact;
import com.luispiquinrey.user.Repository.ContactRepository;
import com.luispiquinrey.user.Repository.RedisRepository;


@Service
public class ServiceUser extends CrudService<Contact, Long> {

    private final RedisRepository redisRepository;

    @Autowired
    public ServiceUser(ContactRepository contactRepository,RedisRepository redisRepository) {
        super(contactRepository, Contact.class);
        this.redisRepository=redisRepository;
    }

    @Override
    public Optional<Contact> findTargetById(Long idTarget) throws SearchException {
        return super.findTargetById(idTarget);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Contact updateTarget(Contact target) throws UpdateException {
        return super.updateTarget(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Contact createTarget(Contact target) throws CreationException {
        return super.createTarget(target);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTarget(Long idTarget) throws DeleteException {
        super.deleteTarget(idTarget);
    }
}