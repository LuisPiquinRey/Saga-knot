package com.luispiquinrey.product.Entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.luispiquinrey.Entities.AuditInfo;
import com.luispiquinrey.Enums.StatusProduct;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "product",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name"})})
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

    @Id
    @Column(name = "id_product", updatable = false, nullable = false)
    private String idProduct=UUID.randomUUID().toString();

    private String name;

    @Embedded
    private AuditInfo auditInfo;

    private String brand;

    @Enumerated(EnumType.STRING)
    private StatusProduct status;

    private float price;

    @Version
    @Column(name = "OPTLOCK")
    private Integer version;

    private Integer stock;

    @ManyToMany(
        targetEntity=Category.class,
        cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="product_category",
        joinColumns= @JoinColumn(name="id_product",referencedColumnName="id_product"),
        inverseJoinColumns= @JoinColumn(name="id_category",referencedColumnName="id_category"))
    private List<Category> categories;

    public Product() {
    }

    public Product(String name, String brand, Float price,Integer stock) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    
}
