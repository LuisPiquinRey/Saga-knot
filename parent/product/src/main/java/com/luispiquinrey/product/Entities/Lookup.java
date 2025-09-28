package com.luispiquinrey.product.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="productLookUp")
public class Lookup {
    @Id
    private String idProduct;

    public Lookup() {
    }
    public Lookup(String idProduct){
        this.idProduct=idProduct;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
