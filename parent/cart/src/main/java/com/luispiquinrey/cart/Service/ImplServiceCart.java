package com.luispiquinrey.cart.Service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.cart.Entities.Cart;

public class ImplServiceCart extends CrudService<Cart, String>{

    public ImplServiceCart(MongoRepository<Cart, String> repositoryGeneric) {
        super(repositoryGeneric, Cart.class);
    }
}
