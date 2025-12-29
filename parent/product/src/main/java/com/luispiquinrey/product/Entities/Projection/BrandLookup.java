package com.luispiquinrey.product.Entities.Projection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand_lookup")
public class BrandLookup {
    @Id
    private String id;
    public BrandLookup() {}
    public BrandLookup(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
