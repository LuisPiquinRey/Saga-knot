package com.luispiquinrey.order.Entities;

import com.luispiquinrey.Enums.StatusOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Order implements Serializable {

    @Id
    private String idOrder = UUID.randomUUID().toString();

    private LocalDateTime orderDate = LocalDateTime.now();

    @PositiveOrZero
    private float subtotal;

    @PositiveOrZero
    private float tax;

    @PositiveOrZero
    private float shippingCost;

    @PositiveOrZero
    private float total;

    @Enumerated(EnumType.STRING)
    private StatusOrder status;

    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Order() {
    }

    public Order(LocalDateTime orderDate, float subtotal, float tax, float shippingCost,
            float total, StatusOrder status, String notes) {
        this.orderDate = orderDate;
        this.subtotal = subtotal;
        this.tax = tax;
        this.shippingCost = shippingCost;
        this.total = total;
        this.status = status;
        this.notes = notes;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Order{"
                + "idOrder='" + idOrder + '\''
                + ", orderDate=" + orderDate
                + ", subtotal=" + subtotal
                + ", tax=" + tax
                + ", shippingCost=" + shippingCost
                + ", total=" + total
                + ", status=" + status
                + ", notes='" + notes + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }
}
