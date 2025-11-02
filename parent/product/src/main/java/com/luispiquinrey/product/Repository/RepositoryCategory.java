package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.Category;

public interface RepositoryCategory extends JpaRepository<Category, String>{
    
}
