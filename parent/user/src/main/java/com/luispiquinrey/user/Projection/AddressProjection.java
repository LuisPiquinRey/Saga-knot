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

@Component
public class AddressProjection {
    private final AddressService addressService;

    @Autowired
    AddressProjection(AddressService addressService){
        this.addressService=addressService;
    }
    @EventHandler
    public void on(AddressCreatedEvent addressCreatedEvent){
        Address address=new Address();
        BeanUtils.copyProperties(addressCreatedEvent, address);
        addressService.createTarget(address);
    }
    @EventHandler
    public void on(AddressUpdatedEvent addressUpdatedEvent){
        Optional<Address> addressOpt = addressService.findTargetById(addressUpdatedEvent.getIdAddress());
        if (!addressOpt.isPresent()) {
            throw new IllegalStateException("No address found with: " + addressUpdatedEvent.getIdAddress());
        }
        Address address = addressOpt.get();
        BeanUtils.copyProperties(addressUpdatedEvent, address);
        addressService.updateTarget(address);
    }
    @EventHandler
    public void on(AddressDeletedEvent addressDeletedEvent){
        addressService.deleteTarget(addressDeletedEvent.getIdAddress());
    }
}
