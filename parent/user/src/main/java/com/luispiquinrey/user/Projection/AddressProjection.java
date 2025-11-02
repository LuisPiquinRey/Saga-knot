package com.luispiquinrey.user.Projection;

import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.user.Entities.Address;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Event.AddressCreatedEvent;
import com.luispiquinrey.user.Event.AddressDeletedEvent;
import com.luispiquinrey.user.Event.AddressUpdatedEvent;
import com.luispiquinrey.user.Event.UserUpdatedEvent;
import com.luispiquinrey.user.Service.AddressService;
import com.luispiquinrey.user.Service.ContactService;

@Component
public class AddressProjection {

    private final AddressService addressService;
    private final ContactService contactService;

    @Autowired
    public AddressProjection(AddressService addressService, ContactService contactService) {
        this.addressService = addressService;
        this.contactService = contactService;
    }

    @EventHandler
    public void on(AddressCreatedEvent event) throws Exception {
        Address address = new Address();
        BeanUtils.copyProperties(event, address);
        contactService.addAddressToUser(event.getIdContact(), address);
    }

    @EventHandler
    public void on(AddressUpdatedEvent event) throws Exception {
        contactService.updateAddressOfUser(event.getIdContact(), event);
    }

    @EventHandler
    public void on(AddressDeletedEvent event) throws Exception {
        contactService.removeAddressFromUser(event.getIdContact(), event.getIdAddress());
    }
}
