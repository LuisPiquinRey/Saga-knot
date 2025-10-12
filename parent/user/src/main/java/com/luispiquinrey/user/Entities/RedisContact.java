package com.luispiquinrey.user.Entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@RedisHash(value = "contact", timeToLive = 3600)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class RedisContact implements Serializable {

    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    private String idContact; 

    @ToString.Include
    @Indexed
    private String username;

    @ToString.Include
    @Indexed
    private String email;

    private String password;

    @ToString.Include
    private String phoneNumber;
    
    private String profileImage;


    public RedisContact(String idContact, String username, String email, String password, String phoneNumber, String profileImage) {
        this.idContact = idContact;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public RedisContact() {
    }

    public String getIdContact() {
        return idContact;
    }

    public void setIdContact(String idContact) {
        this.idContact = idContact;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
