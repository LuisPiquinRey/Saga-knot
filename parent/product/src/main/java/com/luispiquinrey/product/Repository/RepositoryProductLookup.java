package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.Projection.ProductLookup;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryProductLookup extends JpaRepository<ProductLookup, String>{
    boolean existsById(String id);
}
