package com.luispiquinrey.product.Entities;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.luispiquinrey.Entities.AuditInfo;
import com.luispiquinrey.Enums.StatusProduct;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "product",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name"})})
public class Product implements Serializable {

    @Id
    private String idProduct;

    @NotNull @NotBlank @Length(min=3 , max=20)
    private String name;

    @Embedded
    private AuditInfo auditInfo;

    @NotNull @NotBlank @Length(min=3 , max=20)
    private String brand;

    @Enumerated(EnumType.STRING)
    private StatusProduct status;

    @NotNull @PositiveOrZero
    private float price;

    @Version
    @Column(name = "OPTLOCK")
    private Integer version;

    private Integer stock;

    public Product() {
        this.idProduct=UUID.randomUUID().toString();
    }

    public Product(String name, String brand, Float price,Integer stock) {
        this.idProduct=UUID.randomUUID().toString();
        this.name = name;
        this.brand = brand;
        this.price=price;
        this.stock=stock;
    }

    public String getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(String idProduct){
        this.idProduct=idProduct;
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

    public StatusProduct getStatus() {
        return status;
    }

    public void setStatus(StatusProduct status) {
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
}
