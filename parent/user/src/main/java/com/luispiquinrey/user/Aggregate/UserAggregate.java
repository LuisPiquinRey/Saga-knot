package com.luispiquinrey.user.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Event.UserCreatedEvent;

@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private Long idContact;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private String profileImage;

    public UserAggregate() {
    }
    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand){
        UserCreatedEvent userCreatedEvent=UserCreatedEvent.builder().build();
        BeanUtils.copyProperties(createUserCommand, userCreatedEvent);
        AggregateLifecycle.apply(userCreatedEvent);
    }
    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent){
        this.idContact=userCreatedEvent.getIdContact();
        this.username=userCreatedEvent.getUsername();
        this.email=userCreatedEvent.getEmail();
        this.password=userCreatedEvent.getPassword();
        this.phoneNumber=userCreatedEvent.getPhoneNumber();
        this.profileImage=userCreatedEvent.getProfileImage();
    }
}
