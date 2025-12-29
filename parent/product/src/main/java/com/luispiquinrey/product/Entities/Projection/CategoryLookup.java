package com.luispiquinrey.product.Entities.Projection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category_lookup")
public class CategoryLookup {
    @Id
    private String id;

    public CategoryLookup() {}
    public CategoryLookup(String id) {}
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
