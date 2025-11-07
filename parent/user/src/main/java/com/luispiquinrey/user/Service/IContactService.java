package com.luispiquinrey.user.Service;

import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.luispiquinrey.user.Entities.Contact;

public interface IContactService {
    Optional<Contact> findByUsername(String username);
    Optional<Contact> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    void deleteByUsername(@Param("username") String username);
    void deleteByEmail(@Param("email") String email);
}
