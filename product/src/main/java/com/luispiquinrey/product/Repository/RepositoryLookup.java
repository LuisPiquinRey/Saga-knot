package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.Lookup;

public interface RepositoryLookup extends JpaRepository<Lookup, String>{
    
}
