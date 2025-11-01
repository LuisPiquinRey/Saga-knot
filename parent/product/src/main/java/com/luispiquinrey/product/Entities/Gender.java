package com.luispiquinrey.product.Entities;

import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "genders")
public class Gender {

    @Id
    @Column(name = "id_gender", updatable = false, nullable = false)
    private String idGender = UUID.randomUUID().toString();

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    public Gender() {}

    public Gender(String name) {
        this.idGender = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getIdGender() {
        return idGender;
    }

    public void setIdGender(String idGender) {
        this.idGender = idGender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "idGender='" + idGender + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}