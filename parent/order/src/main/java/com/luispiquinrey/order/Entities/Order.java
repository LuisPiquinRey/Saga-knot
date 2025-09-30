package com.luispiquinrey.order.Entities;

import java.util.UUID;

import com.luispiquinrey.Entities.AuditInfo;
import com.luispiquinrey.order.Enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order")
public class Order {

    @Id
    private String idOrder;

    private Status status;

    private AuditInfo auditInfo;

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

}
