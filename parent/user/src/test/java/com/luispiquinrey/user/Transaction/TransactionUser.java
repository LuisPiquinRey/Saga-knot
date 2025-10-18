package com.luispiquinrey.user.Transaction;

import org.axonframework.test.aggregate.FixtureConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.luispiquinrey.user.Entities.Contact;

@SpringBootTest
public class TransactionUser {
    private FixtureConfiguration<Contact> fixture;
}
