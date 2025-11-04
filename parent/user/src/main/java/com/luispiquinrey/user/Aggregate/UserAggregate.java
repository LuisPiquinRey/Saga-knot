package com.luispiquinrey.user.Aggregate;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.user.Command.AddAddressToUserCommand;
import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.RemoveAddressFromUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.Command.UploadImageUserCommand;
import com.luispiquinrey.user.Entities.Address;
import com.luispiquinrey.user.Event.AddressAddedToUserEvent;
import com.luispiquinrey.user.Event.AddressRemovedFromUserEvent;
import com.luispiquinrey.user.Event.UserCreatedEvent;
import com.luispiquinrey.user.Event.UserDeletedEvent;
import com.luispiquinrey.user.Event.UserUpdatedEvent;
import com.luispiquinrey.user.Event.UserUploadedImageEvent;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImageUrl;
    private Map<String, Address> addresses = new HashMap<>();

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder().build();
        BeanUtils.copyProperties(createUserCommand, userCreatedEvent);
        AggregateLifecycle.apply(userCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        UserUpdatedEvent event = UserUpdatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteUserCommand command) {
        UserDeletedEvent event = UserDeletedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UploadImageUserCommand command) {
        UserUploadedImageEvent event = UserUploadedImageEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(AddAddressToUserCommand command) {
        AddressAddedToUserEvent event = AddressAddedToUserEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveAddressFromUserCommand command) {
        AddressRemovedFromUserEvent event = AddressRemovedFromUserEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        this.username = userCreatedEvent.getUsername();
        this.email = userCreatedEvent.getEmail();
        this.password = userCreatedEvent.getPassword();
        this.phoneNumber = userCreatedEvent.getPhoneNumber();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        this.username = userUpdatedEvent.getUsername();
        this.email = userUpdatedEvent.getEmail();
        this.password = userUpdatedEvent.getPassword();
        this.phoneNumber = userUpdatedEvent.getPhoneNumber();
        this.addresses = userUpdatedEvent.getAddresses();
    }

    @EventSourcingHandler
    public void on(UserUploadedImageEvent event) {
        this.profileImageUrl = event.getImageUrl();
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void on(AddressAddedToUserEvent event) {
        Address address = new Address.Builder()
                .idAddress(event.getIdAddress())
                .street(event.getStreet())
                .postalCode(event.getPostalCode())
                .city(event.getCity())
                .state(event.getState())
                .country(event.getCountry())
                .build();
        addresses.put(event.getIdAddress(), address);
    }

    @EventSourcingHandler
    public void on(AddressRemovedFromUserEvent event) {
        addresses.remove(event.getIdAddress());
    }

}
