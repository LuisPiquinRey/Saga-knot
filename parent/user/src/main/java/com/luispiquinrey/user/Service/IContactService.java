package com.luispiquinrey.user.Service;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.user.Entities.Contact;

import jakarta.persistence.QueryHint;

public interface IContactService {
    Optional<Contact> findByUsername(String username);
    Optional<Contact> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    void deleteByUsername(@Param("username") String username);
    void deleteByEmail(@Param("email") String email);
}
