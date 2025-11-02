package com.luispiquinrey.user.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.user.Entities.ContactLookup;
import com.luispiquinrey.user.Event.UserCreatedEvent;
import com.luispiquinrey.user.Event.UserDeletedEvent;
import com.luispiquinrey.user.Repository.ContactLookupRepository;

@Component
@ProcessingGroup("user-group")
public class LookupUserProjection {
        private final ContactLookupRepository lookupRepository;

    @Autowired
    public LookupUserProjection(ContactLookupRepository lookupRepository) {
        this.lookupRepository = lookupRepository;
    }

    @EventHandler
    public void on(UserCreatedEvent event) {
        ContactLookup lookup = new ContactLookup(event.getUsername());
        lookupRepository.save(lookup);
    }

    @EventHandler
    public void on(UserDeletedEvent event) {
        lookupRepository.findByUsername(event.getUsername())
                .ifPresent(lookupRepository::delete);
    }
}
