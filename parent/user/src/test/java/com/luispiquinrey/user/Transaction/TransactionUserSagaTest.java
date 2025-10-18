package com.luispiquinrey.user.Transaction;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Event.UserCreatedEvent;

@SpringBootTest
public class TransactionUserSagaTest {

    private FixtureConfiguration<Contact> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Contact.class);
    }

    @Test
    void testCreateUserCommand_Improved() {
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
}
