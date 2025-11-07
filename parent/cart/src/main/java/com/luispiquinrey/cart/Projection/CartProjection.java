package com.luispiquinrey.cart.Projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.luispiquinrey.cart.Entities.Cart;
import com.luispiquinrey.cart.Entities.CartItem;
import com.luispiquinrey.cart.Event.CartClearedEvent;
import com.luispiquinrey.cart.Event.CreatedCartEvent;
import com.luispiquinrey.cart.Event.DeletedCartEvent;
import com.luispiquinrey.cart.Event.ItemAddedToCartEvent;
import com.luispiquinrey.cart.Event.ItemQuantityUpdatedEvent;
import com.luispiquinrey.cart.Event.ItemRemovedFromCartEvent;
import com.luispiquinrey.cart.Event.UpdatedCartEvent;
import com.luispiquinrey.cart.Repository.RepositoryCart;
import com.luispiquinrey.cart.Repository.RepositoryCartItem;

@Component
@ProcessingGroup("cart-collection")
@Slf4j
public class CartProjection {

    private final RepositoryCart repositoryCart;
    private final RepositoryCartItem repositoryItem;

    @Autowired
    public CartProjection(RepositoryCart repositoryCart, RepositoryCartItem repositoryItem) {
        this.repositoryCart = repositoryCart;
        this.repositoryItem = repositoryItem;
    }

    @EventHandler
    public void on(CreatedCartEvent event) {
        Cart cart = new Cart();
        cart.setIdCart(event.getIdCart());
        cart.setIdUser(event.getIdUser());
        cart.setTotal(0);
        cart.setQuantity(0);
        cart.setItems(new ArrayList<>());
        repositoryCart.save(cart);
        log.info("Cart created with id: {}", event.getIdCart());
    }

    @EventHandler
    public void on(ItemAddedToCartEvent event) {
        Optional<CartItem> existingItemOpt = repositoryItem.findByIdCartAndIdProduct(event.getIdCart(), event.getIdProduct());

        CartItem item;
        if (existingItemOpt.isPresent()) {
            item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + event.getQuantity());
            item.setTotal(item.getTotal() + event.getTotal());
        } else {
            item = new CartItem();
            item.setIdCart(event.getIdCart());
            item.setIdProduct(event.getIdProduct());
            item.setQuantity(event.getQuantity());
            item.setTotal(event.getTotal());
        }

        repositoryItem.save(item);
        recalculateAndSaveCart(event.getIdCart());
        log.info("Item {} added to cart {}", event.getIdProduct(), event.getIdCart());
    }

    @EventHandler
    public void on(ItemRemovedFromCartEvent event) {
        repositoryItem.deleteByIdCartAndIdProduct(event.getIdCart(), event.getIdProduct());
        recalculateAndSaveCart(event.getIdCart());
        log.info("Item {} removed from cart {}", event.getIdProduct(), event.getIdCart());
    }

    @EventHandler
    public void on(ItemQuantityUpdatedEvent event) {
        Optional<CartItem> itemOptional = repositoryItem.findByIdCartAndIdProduct(event.getIdCart(), event.getIdProduct());
        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            item.setQuantity(event.getNewQuantity());
            item.setTotal(event.getNewTotal());
            repositoryItem.save(item);
            recalculateAndSaveCart(event.getIdCart());
            log.info("Item {} quantity updated in cart {}", event.getIdProduct(), event.getIdCart());
        } else {
            log.warn("Item {} not found in cart {} for quantity update", event.getIdProduct(), event.getIdCart());
        }
    }

    @EventHandler
    public void on(CartClearedEvent event) {
        repositoryItem.deleteByIdCart(event.getIdCart());

        Optional<Cart> cartOptional = repositoryCart.findById(event.getIdCart());
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.setTotal(0);
            cart.setQuantity(0);
            cart.setItems(new ArrayList<>());
            repositoryCart.save(cart);
            log.info("Cart {} cleared", event.getIdCart());
        }
    }

    @EventHandler
    public void on(UpdatedCartEvent event) {
        Optional<Cart> cartOptional = repositoryCart.findById(event.getIdCart());
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.setTotal(event.getTotal());
            cart.setQuantity(event.getQuantity());

            if (event.getItems() != null && !event.getItems().isEmpty()) {
                repositoryItem.deleteByIdCart(event.getIdCart());

                for (CartItem item : event.getItems()) {
                    CartItem newItem = new CartItem();
                    newItem.setIdCart(event.getIdCart());
                    newItem.setIdProduct(item.getIdProduct());
                    newItem.setQuantity(item.getQuantity());
                    newItem.setTotal(item.getTotal());
                    repositoryItem.save(newItem);
                }
            }

            repositoryCart.save(cart);
            log.info("Cart {} updated", event.getIdCart());
        }
    }

    @EventHandler
    public void on(DeletedCartEvent event) {
        repositoryItem.deleteByIdCart(event.getIdCart());
        repositoryCart.deleteById(event.getIdCart());
        log.info("Cart {} deleted", event.getIdCart());
    }

    private void recalculateAndSaveCart(String idCart) {
        Optional<Cart> cartOptional = repositoryCart.findById(idCart);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<CartItem> items = repositoryItem.findByIdCart(idCart);

            int totalQuantity = 0;
            float totalAmount = 0;

            for (CartItem item : items) {
                totalQuantity += item.getQuantity();
                totalAmount += item.getTotal();
            }

            cart.setQuantity(totalQuantity);
            cart.setTotal(totalAmount);
            cart.setItems(items);

            repositoryCart.save(cart);
        }
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) {
        log.error("General exception in CartProjection: {}", exception.getMessage(), exception);
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void on(IllegalArgumentException exception) {
        log.error("IllegalArgumentException in CartProjection: {}", exception.getMessage(), exception);
    }
}
