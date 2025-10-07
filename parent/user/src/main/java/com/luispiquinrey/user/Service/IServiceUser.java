package com.luispiquinrey.user.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Error.DeleteException;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Error.UpdateException;



public interface IServiceUser {

    Optional<Contact> findByUsername(String username) throws SearchException;

    Optional<Contact> findByEmail(String email) throws SearchException;
    Optional<Contact> findByIdOptional(Long idContact) throws SearchException;
    Optional<Contact> findByUsernameOrEmail(String username, String email) throws SearchException;

    Boolean existsByEmail(String email) throws SearchException;

    Boolean existsByUsername(String username) throws SearchException;

    Boolean existsById(Long idContact) throws SearchException;

    Page<Contact> findAll(Pageable pageable) throws SearchException;


    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.REPEATABLE_READ,
        rollbackFor = Exception.class
    )
    void deleteByUsername(String username) throws DeleteException;

    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.REPEATABLE_READ,
        rollbackFor = Exception.class
    )
    void deleteByEmail(String email) throws DeleteException;

    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        rollbackFor = Exception.class
    )
    Contact save(Contact contact) throws UpdateException;

    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        rollbackFor = Exception.class
    )
    Contact update(Contact contact) throws UpdateException;
}