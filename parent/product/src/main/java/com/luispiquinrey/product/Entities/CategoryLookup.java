package com.luispiquinrey.product.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="category_lookup")
public class CategoryLookup {
    @Id
    private String idCategory;

    public CategoryLookup() {
    }
    public CategoryLookup(String idCategory){
        this.idCategory=idCategory;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }
}
