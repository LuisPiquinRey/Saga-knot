package com.luispiquinrey.user.Handler;

import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Queries.FindUserByEmailQuery;
import com.luispiquinrey.user.Queries.FindUserByIdQuery;
import com.luispiquinrey.user.Queries.FindUserByUsernameQuery;
import com.luispiquinrey.user.Service.ContactService;

@Component
public class UserQueryHandler {
    private final ContactService contactService;

    @Autowired
    public UserQueryHandler(ContactService contactService){
        this.contactService=contactService;
    }
    @QueryHandler
    public Optional<Contact> findByUsername(FindUserByUsernameQuery query){
        return contactService.findByUsername(query.getUsername());
    }
    @QueryHandler
    public Optional<Contact> findByEmail(FindUserByEmailQuery query){
        return contactService.findByEmail(query.getEmail());
    }
    @QueryHandler
    public Optional<Contact> findById(FindUserByIdQuery query) {
        return contactService.findTargetById(query.getIdContact());
    }
}
