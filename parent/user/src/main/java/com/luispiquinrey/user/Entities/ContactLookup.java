package com.luispiquinrey.user.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="contact_lookup")
public class ContactLookup {
    @Id
    private Long idContact;
    
    public ContactLookup(){
    }
    public ContactLookup(Long idContact) {
        this.idContact = idContact;
    }
    public Long getIdContact() {
        return idContact;
    }
    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }
}
