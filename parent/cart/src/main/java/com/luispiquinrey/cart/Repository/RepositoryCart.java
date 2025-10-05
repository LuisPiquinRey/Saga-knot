package com.luispiquinrey.cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.cart.Entities.Cart;

public interface RepositoryCart extends MongoRepository<Cart, String>{
}
