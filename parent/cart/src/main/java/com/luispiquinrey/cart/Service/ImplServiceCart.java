package com.luispiquinrey.cart.Service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luispiquinrey.Service.GenericCrudService;
import com.luispiquinrey.cart.Entities.Cart;

public class ImplServiceCart extends GenericCrudService<Cart, String>{
    
    public ImplServiceCart(MongoRepository<Cart, String> repositoryGeneric) {
        super(repositoryGeneric);
    }
}
