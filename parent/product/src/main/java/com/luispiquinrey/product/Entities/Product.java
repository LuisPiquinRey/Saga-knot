package com.luispiquinrey.product.Entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.luispiquinrey.common.Entities.AuditInfo;
import com.luispiquinrey.common.Entities.BaseEntity;
import com.luispiquinrey.common.Enums.StatusProduct;

import jakarta.persistence.*;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})})
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity<String> implements Serializable {

    @Id
    @Column(name = "id_product", updatable = false, nullable = false)
    private String idProduct;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 5, max = 100, message = "Product name must be between 5 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "url_image")
    private String urlImage;

    @Embedded
    private AuditInfo auditInfo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Product status is required")
    private StatusProduct status;

    @PositiveOrZero(message = "Price must be zero or positive")
    @Column(nullable = false)
    private float price;

    @Version
    @Column(name = "OPTLOCK")
    private Integer version;

    private Integer stock = 0;

    @ManyToMany(targetEntity = Category.class, cascade = CascadeType.REMOVE)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "id_product", referencedColumnName = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_category", referencedColumnName = "id_category"))
    private List<Category> categories;

    @Embedded
    private Gender gender;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_brand")
    private Brand brand;

    public Product() {
    }

    public Product(String idProduct,String name, StatusProduct status, float price, Integer stock) {
        this.idProduct = idProduct;
        this.name = name;
        this.status = status;
        this.price = price;
        this.stock = stock;
    }

    public Product(String idProduct,String name, StatusProduct status, float price, Integer stock, List<Category> categories,
                   Gender gender, Brand brand) {
        this.idProduct = idProduct;
        this.name = name;
        this.status = status;
        this.price = price;
        this.stock = stock;
        this.categories = categories;
        this.gender = gender;
        this.brand = brand;
    }
    public Product(String idProduct, String name, StatusProduct status, float price, Integer stock, String urlImage) {
        this.idProduct = idProduct;
        this.name = name;
        this.status = status;
        this.price = price;
        this.stock = stock;
        this.urlImage = urlImage;
    }
    public Product(String idProduct, String name, StatusProduct status, float price, Integer stock,
                   List<Category> categories, Gender gender, Brand brand, String urlImage) {
        this.idProduct = idProduct;
        this.name = name;
        this.status = status;
        this.price = price;
        this.stock = stock;
        this.categories = categories;
        this.gender = gender;
        this.brand = brand;
        this.urlImage = urlImage;
    }
    public Product(String idProduct, String name, StatusProduct status, String urlImage) {
        this.idProduct = idProduct;
        this.name = name;
        this.status = status;
        this.urlImage = urlImage;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }

    public StatusProduct getStatus() { return status; }
    public void setStatus(StatusProduct status) { this.status = status; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public AuditInfo getAuditInfo() { return auditInfo; }
    public void setAuditInfo(AuditInfo auditInfo) { this.auditInfo = auditInfo; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    @Override
    public String getId() { return idProduct; }
    @Override
    public void setId(String id) { this.idProduct = id; }
}
