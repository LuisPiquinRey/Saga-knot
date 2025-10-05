package com.luispiquinrey.cart.Projection;

import java.util.List;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.luispiquinrey.cart.Command.Event.CreatedCartEvent;
import com.luispiquinrey.cart.Entities.Item;
import com.luispiquinrey.cart.Entities.Cart;
import com.luispiquinrey.cart.Repository.RepositoryItem;
import com.luispiquinrey.cart.Repository.RepositoryCart;

@Component
@ProcessingGroup("order-collection")
public class CartProjection {

    private final RepositoryCart repositoryOrder;
    private final RepositoryItem repositoryItem;

    @Autowired
    public CartProjection(RepositoryCart repositoryOrder,RepositoryItem repositoryItem){
        this.repositoryOrder=repositoryOrder;
        this.repositoryItem=repositoryItem;
    }
    @EventHandler
    public void on(CreatedCartEvent createdOrderEvent){
        Cart order=new Cart();
        BeanUtils.copyProperties(createdOrderEvent,order);
        repositoryOrder.save(order);
    }
}
