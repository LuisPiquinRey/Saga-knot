package com.luispiquinrey.product.Entities.Projection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand_lookup")
public class BrandLookup {
    @Id
    @Column(name = "id_brand")
    private String idBrand;

    private String name;

    private String description;

    public BrandLookup() {}

    public BrandLookup(String idBrand) {
        this.idBrand = idBrand;
    }

    public String getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(String idBrand) {
        this.idBrand = idBrand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
