package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.Brand;

public interface RepositoryBrand extends JpaRepository<Brand, String>{
    
}
