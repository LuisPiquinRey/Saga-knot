package com.luispiquinrey.user.Transaction;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Event.UserCreatedEvent;
import com.luispiquinrey.user.Event.UserDeletedEvent;
import com.luispiquinrey.user.Event.UserUpdatedEvent;

@SpringBootTest
public class TransactionUserSagaTest {

    private FixtureConfiguration<Contact> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Contact.class);
    }

    @Test
    void testCreateUserCommand() {
        CreateUserCommand command = CreateUserCommand.builder()
                .idContact(1L)
                .username("luispiquin")
                .email("luis@email.com")
                .password("securePassword123")
                .phoneNumber("+34123456789")
                .profileImage("profile.jpg")
                .build();

        UserCreatedEvent expectedEvent = UserCreatedEvent.builder()
                .idContact(1L)
                .username("luispiquin")
                .email("luis@email.com")
                .password("securePassword123")
                .phoneNumber("+34123456789")
                .profileImage("profile.jpg")
                .build();

        fixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(expectedEvent);
    }
    @Test
    void testUpdateUserCommand(){
        UpdateUserCommand command = UpdateUserCommand.builder()
                .idContact(1L)
                .username("luispiquin")
                .email("luis@email.com")
                .password("securePassword123")
                .phoneNumber("+34123456789")
                .profileImage("profile.jpg")
                .build();
        UserUpdatedEvent expectedEvent = UserUpdatedEvent.builder()
                .idContact(1L)
                .username("luispiquin")
                .email("luis@email.com")
                .password("securePassword123")
                .phoneNumber("+34123456789")
                .profileImage("profile.jpg")
                .build();
        fixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(expectedEvent);
    }
    @Test 
    void testDeleteUserCommand(){
        DeleteUserCommand command=DeleteUserCommand.builder()
                .idContact(2L)
                .build();
        UserDeletedEvent expectedEvent= UserDeletedEvent.builder()
                .idContact(2L)
                .build();
        fixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(expectedEvent);
    }
}
