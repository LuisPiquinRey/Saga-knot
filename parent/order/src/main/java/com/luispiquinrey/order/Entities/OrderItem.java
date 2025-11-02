package com.luispiquinrey.order.Entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    private Integer quantity;

    @NotNull
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    public OrderItem() {
    }

    public OrderItem(@NotNull String idProduct, @Positive Integer quantity, @NotNull BigDecimal subtotal,
            BigDecimal discount) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.discount = discount;
    }

    public OrderItem(@Positive Integer quantity, @NotNull BigDecimal subtotal, BigDecimal discount) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
