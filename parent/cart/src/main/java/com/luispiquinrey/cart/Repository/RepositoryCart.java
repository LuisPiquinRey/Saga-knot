package com.luispiquinrey.cart.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.cart.Entities.Cart;

public interface RepositoryCart extends MongoRepository<Cart, String> {

    Optional<Cart> findByIdUser(String idUser);
}
