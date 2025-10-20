package com.luispiquinrey.user.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.Service.SenderCommandService;
import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.DTOs.RequestContactDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/command")
public class UserController {

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody RequestContactDto requestContactDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        } else {
            try {
                Contact contact = new Contact.Builder()
                    .username(requestContactDto.username())
                    .email(requestContactDto.email())
                    .password(requestContactDto.password())
                    .phoneNumber(requestContactDto.phoneNumber())
                    .build();
                CreateUserCommand createUserCommand=CreateUserCommand.builder()
                    .email(contact.getEmail())
                    .idContact(contact.getIdContact())
                    .password(contact.getPassword())
                    .phoneNumber(contact.getPhoneNumber())
                    .profileImage(contact.getProfileImage())
                    .build();
                SenderCommandService senderCommandService=new SenderCommandService();
                senderCommandService.send(createUserCommand);

                return ResponseEntity.ok()
                        .body(Map.of(
                                "message", "User created successfully"
                        ));
            } catch (Exception e) {
                return ResponseEntity.internalServerError()
                        .body(Map.of("error", "Failed to create product: " + e.getMessage()));
            }
        }
    }
}
