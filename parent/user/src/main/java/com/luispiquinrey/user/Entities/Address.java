package com.luispiquinrey.user.Entities;

import java.io.Serializable;
import java.util.UUID;

import com.luispiquinrey.Entities.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "address")
public class Address extends BaseEntity<String> implements Serializable {

    @Id
    @Column(name = "id_address", nullable = false, updatable = false, length = 36)
    private String idAddress = UUID.randomUUID().toString();

    @NotBlank(message = "Street cannot be blank")
    @Size(max = 100, message = "Street must not exceed 100 characters")
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Size(max = 10, message = "Postal code must not exceed 10 characters")
    @Pattern(regexp = "^[A-Za-z0-9\\- ]*$", message = "Postal code can only contain letters, numbers, and hyphens")
    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 50, message = "City must not exceed 50 characters")
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Size(max = 50, message = "State must not exceed 50 characters")
    @Column(name = "state", length = 50)
    private String state;

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    public Address() {
    }

    public Address(String street, String postalCode, String city, String state, String country) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    @Override
    public String getId() {
        return idAddress;
    }

    @Override
    public void setId(String id) {
        this.idAddress = id;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}