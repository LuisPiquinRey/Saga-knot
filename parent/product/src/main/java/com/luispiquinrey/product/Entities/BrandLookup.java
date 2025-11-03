package com.luispiquinrey.product.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="brand_lookup")
public class BrandLookup {
    @Id
    private String idBrand;

    public BrandLookup() {
    }
    public BrandLookup(String idBrand){
        this.idBrand=idBrand;
    }

    public String getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(String idBrand) {
        this.idBrand = idBrand;
    }
}
