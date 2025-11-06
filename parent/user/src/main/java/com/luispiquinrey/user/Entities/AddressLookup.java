package com.luispiquinrey.user.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="address_lookup")
public class AddressLookup {
    @Id
    private String idAddress;

    public AddressLookup(){
    }

    public AddressLookup(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }
    
}
