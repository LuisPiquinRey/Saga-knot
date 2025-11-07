package com.luispiquinrey.cart.Handler;

import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.cart.Entities.Cart;
import com.luispiquinrey.cart.Queries.FindCartById;
import com.luispiquinrey.cart.Service.ServiceCart;

@Component
public class CartQueryHandler {
    private final ServiceCart serviceCart;

    @Autowired
    public CartQueryHandler(ServiceCart serviceCart) {
        this.serviceCart=serviceCart;
    }
    @QueryHandler
    public Optional<Cart> findById(FindCartById findCartById){
        return serviceCart.findTargetById(findCartById.getIdCart());
    }
}
