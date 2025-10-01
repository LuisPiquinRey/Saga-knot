package com.luispiquinrey.order.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import com.luispiquinrey.Entities.AuditInfo;
import com.luispiquinrey.order.Enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "order")
public class Order implements Serializable{

    @Id
    private String idOrder;

    @Column(columnDefinition = "PENDING")
    private Status status;

    private AuditInfo auditInfo;

    @PositiveOrZero
    private float total;

    @PositiveOrZero
    @Max(value = 1000)
    private int quantity;

    @Version
    @Column(name="OPTLOCK")
    private int version;

    @OneToMany(mappedBy = "order")
    private ArrayList<Item> items;

    public Order() {
        this.idOrder = UUID.randomUUID().toString();
        this.status = Status.PENDING; 
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
