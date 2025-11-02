package com.luispiquinrey.user.Aggregate;

import com.luispiquinrey.user.Command.CreateAddressCommand;
import com.luispiquinrey.user.Command.DeleteAddressCommand;
import com.luispiquinrey.user.Command.UpdateAddressCommand;
import com.luispiquinrey.user.Event.AddressCreatedEvent;
import com.luispiquinrey.user.Event.AddressDeletedEvent;
import com.luispiquinrey.user.Event.AddressUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class AddressAggregate {
    
    @AggregateIdentifier
    private String idAddress;
    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private Long idContact;
    
    public AddressAggregate() {
    }
    
    @CommandHandler
    public AddressAggregate(CreateAddressCommand createAddressCommand) {
        AddressCreatedEvent addressCreatedEvent = AddressCreatedEvent.builder().build();
        BeanUtils.copyProperties(createAddressCommand, addressCreatedEvent);
        AggregateLifecycle.apply(addressCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateAddressCommand updateAddressCommand) {
        AddressUpdatedEvent event = AddressUpdatedEvent.builder().build();
        BeanUtils.copyProperties(updateAddressCommand, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteAddressCommand deleteAddressCommand) {
        AggregateLifecycle.apply(AddressDeletedEvent.builder()
                .idAddress(deleteAddressCommand.getIdAddress())
                .build());
    }

    @EventSourcingHandler
    public void on(AddressCreatedEvent addressCreatedEvent) {
        this.idAddress = addressCreatedEvent.getIdAddress();
        this.street = addressCreatedEvent.getStreet();
        this.postalCode = addressCreatedEvent.getPostalCode();
        this.city = addressCreatedEvent.getCity();
        this.state = addressCreatedEvent.getState();
        this.country = addressCreatedEvent.getCountry();
        this.idContact = addressCreatedEvent.getIdContact();
    }

    @EventSourcingHandler
    public void on(AddressUpdatedEvent addressUpdatedEvent) {
        this.street = addressUpdatedEvent.getStreet();
        this.postalCode = addressUpdatedEvent.getPostalCode();
        this.city = addressUpdatedEvent.getCity();
        this.state = addressUpdatedEvent.getState();
        this.country = addressUpdatedEvent.getCountry();
    }

    @EventSourcingHandler
    public void on(AddressDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}