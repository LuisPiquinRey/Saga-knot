package com.luispiquinrey.product.Entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.luispiquinrey.product.Enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

@Entity
@Table(name="product", 
    uniqueConstraints= {@UniqueConstraint(columnNames={"name"})})
public class Product implements Serializable{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idProduct;

    private String name;

    @Embedded
    private AuditInfo auditInfo;

    private String brand;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    private float price;
    @Version
    @Column(name="OPTLOCK")
    private Integer version;
    public Product(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }
    public Long getIdProduct() {
        return idProduct;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public AuditInfo getAuditInfo() {
        return auditInfo;
    }
    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }
}
