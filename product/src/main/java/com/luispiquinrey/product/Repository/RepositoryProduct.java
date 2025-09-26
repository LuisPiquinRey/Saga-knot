package com.luispiquinrey.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.product.Entities.Product;


@Repository
public interface RepositoryProduct extends JpaRepository<Product, Long>{
}
