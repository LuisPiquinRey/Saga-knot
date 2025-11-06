package com.luispiquinrey.user.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Entities.ContactLookup;

public interface ContactLookupRepository extends JpaRepository<ContactLookup, String>{
    Optional<ContactLookup> findByUsername(String username);

    Optional<ContactLookup> findByEmail(String email);
}
