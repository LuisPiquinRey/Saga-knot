package com.luispiquinrey.order.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name="item")
public class OrderItem {
    @Id
    @Column(name = "id_order_item", updatable = false, nullable = false)
    private String idOrderItem;

    @Column(name = "id_product", nullable = false)
    @NotNull
    private String idProduct;

    @Positive
    @Column(nullable = false)
    private int quantity;

    @PositiveOrZero
    @Column(name = "subtotal", nullable = false)
    private float subtotal;

    @PositiveOrZero
    @Column(name = "discount")
    private float discount;

    public OrderItem() {
    }

    public OrderItem(@NotNull String idProduct, int quantity, float subtotal, float discount) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.discount = discount;
    }

    public OrderItem(int quantity, float subtotal, float discount) {
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.discount = discount;
    }

    public String getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(String idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
