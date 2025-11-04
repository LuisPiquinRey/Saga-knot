package com.luispiquinrey.user.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_lookup")
public class ContactLookup {

    @Id
    private String username;
    private String email;

    public ContactLookup() {
    }
    public ContactLookup(String username) {
        this.username = username;
    }
    
    public ContactLookup(String username, String email) {
        this.username = username;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}