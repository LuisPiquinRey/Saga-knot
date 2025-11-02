package com.luispiquinrey.user.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.Command.UploadImageUserCommand;
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

    private MultipartFile profileImage;

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder().build();
        BeanUtils.copyProperties(createUserCommand, userCreatedEvent);
        AggregateLifecycle.apply(userCreatedEvent);
    }

    @CommandHandler
    public UserAggregate(UpdateUserCommand updateUserCommand) {
        UserUpdatedEvent event = UserUpdatedEvent.builder().build();
        BeanUtils.copyProperties(updateUserCommand, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public UserAggregate(DeleteUserCommand deleteUserCommand) {
        AggregateLifecycle.apply(UserDeletedEvent.builder()
                .username(deleteUserCommand.getUsername())
                .build());
    }

    @CommandHandler
    public UserAggregate(UploadImageUserCommand uploadImageUserCommand) {
        AggregateLifecycle.apply(UserUploadedImageEvent.builder()
                .image(uploadImageUserCommand.getImage())
                .username(uploadImageUserCommand.getUsername())
                .build());
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
    }

    @EventSourcingHandler
    public void on(UserUploadedImageEvent userUploadedImageEvent) {
        this.username = userUploadedImageEvent.getUsername();
        this.profileImage = userUploadedImageEvent.getImage();
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
