package com.luispiquinrey.user.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Entities.DTOs.RequestContactDto;
import com.luispiquinrey.user.Error.DeleteException;
import com.luispiquinrey.user.Error.SearchException;
import com.luispiquinrey.user.Error.UpdateException;
import com.luispiquinrey.user.Service.ImplServiceUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class RestControllerUser {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerUser.class);
    private final ImplServiceUser implServiceUser;

    @Autowired
    public RestControllerUser(ImplServiceUser implServiceUser) {
        this.implServiceUser = implServiceUser;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestContactDto requestContact,
            BindingResult binding) {
        if (binding.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(binding.getAllErrors().stream()
                            .map(err -> err.getDefaultMessage())
                            .toList());
        }
        try {
            Contact contact = new Contact.Builder()
                    .username(requestContact.username())
                    .email(requestContact.email())
                    .password(requestContact.password())
                    .phoneNumber(requestContact.phoneNumber())
                    .build();

            Contact savedContact = implServiceUser.save(contact);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
        } catch (UpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Create error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error");
        }
    }

    @GetMapping("/{idContact}")
    public ResponseEntity<?> findById(@PathVariable Long idContact) {
        try {
            Optional<Contact> contact;
            contact = implServiceUser.findByIdOptional(idContact);
            return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Search error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try {
            Optional<Contact> contact = implServiceUser.findByUsername(username);
            return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Search error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        try {
            Optional<Contact> contact = implServiceUser.findByEmail(email);
            return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Search error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Contact> contacts = implServiceUser.findAll(pageable);
            return ResponseEntity.ok(contacts);
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Search error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/exists/id/{idContact}")
    public Boolean existsContact(@PathVariable Long idContact) {
        try {
            return implServiceUser.existsById(idContact);
        } catch (SearchException e) {
            return false;
        }
    }

    @GetMapping("/exists/username/{username}")
    public Boolean existsByUsername(@PathVariable String username) {
        try {
            return implServiceUser.existsByUsername(username);
        } catch (SearchException e) {
            return false;
        }
    }

    @GetMapping("/exists/email/{email}")
    public Boolean existsByEmail(@PathVariable String email) {
        try {
            return implServiceUser.existsByEmail(email);
        } catch (SearchException e) {
            return false;
        }
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
            @Valid @RequestBody RequestContactDto requestContact,
            BindingResult binding) {
        if (binding.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(binding.getAllErrors().stream()
                            .map(err -> err.getDefaultMessage())
                            .toList());
        }
        try {
            Optional<Contact> existingContact = implServiceUser.findByUsername(username);
            if (existingContact.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Contact contact = new Contact.Builder()
                    .id(existingContact.get().getIdContact())
                    .username(requestContact.username())
                    .email(requestContact.email())
                    .password(requestContact.password())
                    .phoneNumber(requestContact.phoneNumber())
                    .version(existingContact.get().getVersion())
                    .build();
            Contact updatedContact = implServiceUser.update(contact);
            return ResponseEntity.ok(updatedContact);
        } catch (UpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable String username) {
        try {
            Optional<Contact> contact = implServiceUser.findByUsername(username);
            if (contact.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            implServiceUser.deleteByUsername(username);
            return ResponseEntity.ok("User deleted successfully: " + username);
        } catch (DeleteException | SearchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) {
        try {
            Optional<Contact> contact = implServiceUser.findByEmail(email);
            if (contact.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            implServiceUser.deleteByEmail(email);
            return ResponseEntity.ok("User deleted successfully: " + email);
        } catch (DeleteException | SearchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
