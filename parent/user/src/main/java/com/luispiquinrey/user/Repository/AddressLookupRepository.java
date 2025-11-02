package com.luispiquinrey.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.user.Entities.AddressLookup;

public interface AddressLookupRepository extends JpaRepository<AddressLookup,String>{
    
}
