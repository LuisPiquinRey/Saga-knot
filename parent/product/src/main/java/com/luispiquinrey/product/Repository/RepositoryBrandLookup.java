package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luispiquinrey.product.Entities.BrandLookup;

public interface RepositoryBrandLookup extends JpaRepository<BrandLookup, String>{

}
