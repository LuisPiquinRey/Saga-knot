package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.ProductLookup;

public interface RepositoryProductLookup extends JpaRepository<ProductLookup, String>{
    
}
