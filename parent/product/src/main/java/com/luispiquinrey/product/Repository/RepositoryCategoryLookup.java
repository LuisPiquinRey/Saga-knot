package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.CategoryLookup;

public interface RepositoryCategoryLookup extends JpaRepository<CategoryLookup, String>{

}
