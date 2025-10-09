package com.luispiquinrey.user.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "contact",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class Contact implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idContact;

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotBlank
    @NonNull
    @Basic
    private String username;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false, unique = true)
    @NonNull
    @Basic
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
            message = "Password must be at least 8 characters with uppercase, lowercase, digit and special character")
    @Column(name = "password", nullable = false)
    @NotBlank
    @NonNull
    @Basic
    private String password;

    @Column(name = "phone_number", length = 20)
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Phone number must be a valid international format")
    @Basic
    private String phoneNumber;

    @Column(name = "profile_image")
    private String profileImage;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "OPTLOCK")
    private Integer version;

    public Contact() {}

    private Contact(Builder builder) {
        this.idContact = builder.idContact;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.profileImage = builder.profileImage;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.version = builder.version;
    }

    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean hasProfileImage() {
        return this.profileImage != null && !this.profileImage.isEmpty();
    }

    @Override
    public String toString() {
        return "\u001B[36m📇 Contact Info:\u001B[0m {\n" + "  \u001B[33m🆔 idUser:\u001B[0m "
                + idContact + ",\n" + "  \u001B[32m👤 Username:\u001B[0m '" + username + "',\n"
                + "  \u001B[34m📧 Email:\u001B[0m '" + email + "',\n"
                + "  \u001B[35m📞 Phone:\u001B[0m '" + phoneNumber + "',\n"
                + "  \u001B[36m🕒 Created:\u001B[0m " + createdAt + ",\n"
                + "  \u001B[36m🔄 Updated:\u001B[0m " + updatedAt + ",\n"
                + "  \u001B[36m📌 Version:\u001B[0m " + version + "\n" + "}";
    }

    public static class Builder {
        private Long idContact;
        private String username;
        private String email;
        private String password;
        private String phoneNumber;
        private String profileImage;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer version;

        public Builder id(Long idContact) {
            this.idContact = idContact;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Contact build() {
            validateRequired();
            return new Contact(this);
        }

        private void validateRequired() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username is required");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password is required");
            }
        }
    }
}


