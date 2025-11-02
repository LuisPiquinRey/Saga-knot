package com.luispiquinrey.product.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="product_lookup")
public class ProductLookup {
    @Id
    private String idProduct;

    public ProductLookup() {
    }
    public ProductLookup(String idProduct){
        this.idProduct=idProduct;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
