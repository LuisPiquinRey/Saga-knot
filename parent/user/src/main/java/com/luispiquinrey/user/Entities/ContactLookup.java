package com.luispiquinrey.user.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_lookup")
public class ContactLookup {

    @Id
    private String username;

    public ContactLookup() {
    }
    public ContactLookup(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}