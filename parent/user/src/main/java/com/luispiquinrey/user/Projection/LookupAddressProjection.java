package com.luispiquinrey.user.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.user.Entities.AddressLookup;
import com.luispiquinrey.user.Event.AddressAddedToUserEvent;
import com.luispiquinrey.user.Event.AddressRemovedFromUserEvent;
import com.luispiquinrey.user.Repository.AddressLookupRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@ProcessingGroup("address-lookup-group")
@Slf4j
public class LookupAddressProjection {

    private final AddressLookupRepository addressLookupRepository;

    @Autowired
    public LookupAddressProjection(AddressLookupRepository addressLookupRepository) {
        this.addressLookupRepository = addressLookupRepository;
    }

    @EventHandler
    public void on(AddressAddedToUserEvent event) {
        try {
            AddressLookup addressLookup = new AddressLookup(event.getIdAddress());
            addressLookupRepository.save(addressLookup);
            log.info("Address lookup created for address ID: {}", event.getIdAddress());
        } catch (Exception e) {
            log.error("Error creating address lookup for address ID: {}", event.getIdAddress(), e);
            throw e;
        }
    }

    @EventHandler
    public void on(AddressRemovedFromUserEvent event) {
        try {
            addressLookupRepository.deleteById(event.getIdAddress());
            log.info("Address lookup deleted for address ID: {}", event.getIdAddress());
        } catch (Exception e) {
            log.error("Error deleting address lookup for address ID: {}", event.getIdAddress(), e);
            throw e;
        }
    }
}
