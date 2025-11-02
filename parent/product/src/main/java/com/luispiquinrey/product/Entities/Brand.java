package com.luispiquinrey.product.Entities;

import java.io.Serializable;
import java.util.UUID;

import com.luispiquinrey.Entities.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "brands")
public class Brand extends BaseEntity<String> implements Serializable{

    @Id
    @Column(name = "id_brand", updatable = false, nullable = false)
    private String idBrand = UUID.randomUUID().toString();

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    public Brand() {}

    public Brand(String name, String description) {
        this.idBrand = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    @Override
    public String getId() {
        return idBrand;
    }

    @Override
    public void setId(String id) {
        this.idBrand=id;
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

    @Override
    public String toString() {
        return "Brand{" +
                "idBrand='" + idBrand + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
