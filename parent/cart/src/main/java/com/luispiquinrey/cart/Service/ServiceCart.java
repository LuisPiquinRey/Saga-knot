package com.luispiquinrey.cart.Service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.cart.Entities.Cart;

public class ServiceCart extends CrudService<Cart, String>{

    public ServiceCart(MongoRepository<Cart, String> repositoryGeneric) {
        super(repositoryGeneric, Cart.class);
    }
}
