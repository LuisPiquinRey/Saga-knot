package com.luispiquinrey.user.Projection;

import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.Error.UpdateException;
import com.luispiquinrey.user.Entities.Address;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Event.AddressAddedToUserEvent;
import com.luispiquinrey.user.Event.AddressRemovedFromUserEvent;
import com.luispiquinrey.user.Event.UserCreatedEvent;
import com.luispiquinrey.user.Event.UserDeletedEvent;
import com.luispiquinrey.user.Event.UserUpdatedEvent;
import com.luispiquinrey.user.Event.UserUploadedImageEvent;
import com.luispiquinrey.user.Repository.AddressRepository;
import com.luispiquinrey.user.Service.ContactService;

import lombok.extern.slf4j.Slf4j;

@Component
@ProcessingGroup("user-group")
@Slf4j
public class UserProjection {

    private final ContactService contactService;
    private final AddressRepository addressRepository;

    @Autowired
    public UserProjection(ContactService contactService,
            AddressRepository addressRepository) {
        this.contactService = contactService;
        this.addressRepository = addressRepository;
    }

    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(userCreatedEvent, contact);
        contactService.createTarget(contact);
    }

    @EventHandler
    public void on(UserDeletedEvent userDeletedEvent) {
        contactService.deleteByUsername(userDeletedEvent.getUsername());
    }

    @EventHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        Optional<Contact> contactOpt = contactService.findByUsername(userUpdatedEvent.getUsername());
        if (!contactOpt.isPresent()) {
            throw new IllegalStateException("No user found with username: " + userUpdatedEvent.getUsername());
        }
        Contact contact = contactOpt.get();
        BeanUtils.copyProperties(userUpdatedEvent, contact);
        contactService.updateTarget(contact);
    }

    @EventHandler
    public void on(UserUploadedImageEvent event) {
        try {
            Optional<Contact> contactOpt = contactService.findByUsername(event.getUsername());
            if (!contactOpt.isPresent()) {
                throw new IllegalStateException("No user found with username: " + event.getUsername());
            }
            Contact contact = contactOpt.get();
            contact.setProfileImage(event.getImageUrl());
            contactService.updateTarget(contact);
            log.info("Profile image updated for user: {}", event.getUsername());
        } catch (UpdateException e) {
            log.error("Failed to update contact with image URL for user: {}", event.getUsername(), e);
            throw new RuntimeException("Failed to update contact with image URL", e);
        } catch (Exception e) {
            log.error("Error handling UserUploadedImageEvent for user: {}", event.getUsername(), e);
            throw e;
        }
    }

    @EventHandler
    public void on(AddressAddedToUserEvent event) {
        try {
            Optional<Contact> contactOpt = contactService.findByUsername(event.getUsername());
            if (!contactOpt.isPresent()) {
                throw new IllegalStateException("No user found with username: " + event.getUsername());
            }
            Contact contact = contactOpt.get();

            Address address = new Address.Builder()
                    .idAddress(event.getIdAddress())
                    .street(event.getStreet())
                    .city(event.getCity())
                    .state(event.getState())
                    .country(event.getCountry())
                    .postalCode(event.getPostalCode())
                    .contact(contact)
                    .build();
            addressRepository.save(address);
            log.info("Address {} added to user: {}", event.getIdAddress(), event.getUsername());
        } catch (Exception e) {
            log.error("Error adding address {} to user: {}", event.getIdAddress(), event.getUsername(), e);
            throw e;
        }
    }
    @EventHandler
    public void on(AddressRemovedFromUserEvent event) {
        if (addressRepository.existsById(event.getIdAddress())) {
            addressRepository.deleteById(event.getIdAddress());
            log.info("Address {} removed from projection", event.getIdAddress());
        } else {
            log.warn("Attempted to remove non-existent address: {}", event.getIdAddress());
        }
    }
}
