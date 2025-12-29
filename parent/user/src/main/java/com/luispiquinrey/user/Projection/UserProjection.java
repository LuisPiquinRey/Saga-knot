package com.luispiquinrey.user.Projection;

import java.util.Optional;

import com.luispiquinrey.common.Error.CreationException;
import com.luispiquinrey.common.Utilities.DataService;
import com.luispiquinrey.common.Utilities.IDataService;
import com.luispiquinrey.common.Utilities.RedisDecorator;
import com.luispiquinrey.user.Error.DeleteException;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Repository.ContactRepository;
import com.luispiquinrey.user.Service.SecureDecorator;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.luispiquinrey.common.Error.UpdateException;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Event.UserCreatedEvent;
import com.luispiquinrey.user.Event.UserDeletedEvent;
import com.luispiquinrey.user.Event.UserUpdatedEvent;
import com.luispiquinrey.user.Repository.AddressRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@ProcessingGroup("user-group")
@Slf4j
public class UserProjection {

    private final IDataService<Contact, Long> baseDecorator;
    private final AddressRepository addressRepository;

    @Autowired
    public UserProjection(ContactRepository contactRepository,
                          AddressRepository addressRepository,
                          RedisTemplate<String, Contact> redisTemplate,
                          Keycloak keycloak) {

        this.addressRepository = addressRepository;

        DataService<Contact, Long> dataService = new DataService<>(contactRepository, Contact.class);
        SecureDecorator<Contact, Long> secureDecorator =
                new SecureDecorator<>(dataService, keycloak, keycloak.realm("master").users());
        this.baseDecorator = new RedisDecorator<>(secureDecorator, redisTemplate, Contact.class);
    }

    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent) throws CreationException {
        Contact contact = new Contact();
        BeanUtils.copyProperties(userCreatedEvent, contact);
        baseDecorator.createTarget(contact);
    }

    @EventHandler
    public void on(UserDeletedEvent userDeletedEvent) throws DeleteException {
        Optional<Contact> contactOpt = baseDecorator.findByUsername(userDeletedEvent.getUsername());
        contactOpt.ifPresent(c -> baseDecorator.deleteTarget(c.getIdContact()));
    }

    @EventHandler
    public void on(UserUpdatedEvent userUpdatedEvent) throws UpdateException, SearchException {
        Optional<Contact> contactOpt = baseDecorator.findByUsername(userUpdatedEvent.getUsername());
        if (!contactOpt.isPresent()) {
            throw new IllegalStateException("No user found with username: " + userUpdatedEvent.getUsername());
        }
        Contact contact = contactOpt.get();
        BeanUtils.copyProperties(userUpdatedEvent, contact);
        baseDecorator.updateTarget(contact);
    }
}
