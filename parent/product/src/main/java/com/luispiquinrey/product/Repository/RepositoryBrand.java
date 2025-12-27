package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.Brand;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryBrand extends JpaRepository<Brand, String>{
    
}
