package com.luispiquinrey.user.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.user.Entities.Contact;

import jakarta.persistence.QueryHint;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @QueryHints({
        @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value = "USE"),
        @QueryHint(name = "jakarta.persistence.cache.storeMode", value = "USE")
    })
    Optional<Contact> findByUsername(String username);

    @QueryHints({
        @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value = "USE"),
        @QueryHint(name = "jakarta.persistence.cache.storeMode", value = "USE")
    })
    Optional<Contact> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<Contact> findAllUsers();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @Query("DELETE FROM Contact c WHERE c.username = :username")
    void deleteByUsername(@Param("username") String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @Query("DELETE FROM Contact c WHERE c.email = :email")
    void deleteByEmail(@Param("email") String email);

}
