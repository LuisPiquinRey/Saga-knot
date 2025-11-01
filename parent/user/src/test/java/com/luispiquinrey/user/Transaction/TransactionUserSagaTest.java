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

