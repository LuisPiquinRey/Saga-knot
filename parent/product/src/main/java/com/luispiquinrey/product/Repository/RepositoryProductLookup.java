package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.ProductLookup;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryProductLookup extends JpaRepository<ProductLookup, String>{
    
}
