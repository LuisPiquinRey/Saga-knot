package com.luispiquinrey.cart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.cart.Entities.CartItem;

@Repository
public interface RepositoryItem extends MongoRepository<CartItem, String> {

    List<CartItem> findByIdCart(String idCart);

    Optional<CartItem> findByIdCartAndIdProduct(String idCart, String idProduct);

    void deleteByIdCart(String idCart);

    void deleteByIdCartAndIdProduct(String idCart, String idProduct);
}
