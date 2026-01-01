package com.luispiquinrey.product.Entities.Projection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category_lookup")
public class CategoryLookup {

    @Id
    @Column(name = "id_category")
    private String idCategory;

    private String name;

    private String description;

    private String image;

    public CategoryLookup() {}

    public CategoryLookup(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}