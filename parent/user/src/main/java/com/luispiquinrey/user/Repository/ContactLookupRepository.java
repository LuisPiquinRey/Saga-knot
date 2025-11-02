package com.luispiquinrey.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.user.Entities.ContactLookup;

public interface ContactLookupRepository extends JpaRepository<ContactLookup, Long>{
    
}
