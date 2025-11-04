package com.luispiquinrey.user.Entities;

import java.io.Serializable;
import java.util.UUID;

import com.luispiquinrey.Entities.BaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Contact contact;

    public Address() {
    }

    public Address(String street, String postalCode, String city, String state, String country) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Address(String street, String postalCode, String city, String state, String country, Contact contact) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.contact = contact;
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    public static class Builder {

        private String idAddress = UUID.randomUUID().toString();
        private String street;
        private String postalCode;
        private String city;
        private String state;
        private String country;
        private Contact contact;

        public Builder idAddress(String idAddress) {
            this.idAddress = idAddress;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder contact(Contact contact) {
            this.contact = contact;
            return this;
        }

        public Address build() {
            validateRequired();
            Address address = new Address();
            address.setIdAddress(this.idAddress);
            address.setStreet(this.street);
            address.setPostalCode(this.postalCode);
            address.setCity(this.city);
            address.setState(this.state);
            address.setCountry(this.country);
            address.setContact(this.contact);
            return address;
        }

        private void validateRequired() {
            if (street == null || street.trim().isEmpty()) {
                throw new IllegalArgumentException("Street is required");
            }
            if (city == null || city.trim().isEmpty()) {
                throw new IllegalArgumentException("City is required");
            }
            if (country == null || country.trim().isEmpty()) {
                throw new IllegalArgumentException("Country is required");
            }
        }
    }
    @Override
    public String toString() {
        return "\u001B[36m🏠 Address:\u001B[0m {\n"
                + "  \u001B[33m📍 id:\u001B[0m " + idAddress + ",\n"
                + "  \u001B[32m🏘️ Street:\u001B[0m '" + street + "',\n"
                + "  \u001B[34m🏙️ City:\u001B[0m '" + city + "',\n"
                + "  \u001B[35m🌎 Country:\u001B[0m '" + country + "'\n"
                + "}";
    }
}
