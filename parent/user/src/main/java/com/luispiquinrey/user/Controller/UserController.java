package com.luispiquinrey.user.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.Service.SenderCommandService;
import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.DTOs.RequestContactDto;
import com.luispiquinrey.user.Entities.Contact;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/command")
public class UserController {

    private final SenderCommandService senderCommandService;

    @Autowired
    public UserController(SenderCommandService senderCommandService){
        this.senderCommandService = senderCommandService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody RequestContactDto requestContactDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        } else {
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
            return handleCommand(createUserCommand);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        DeleteUserCommand deleteUserCommand=DeleteUserCommand.builder()
                .idContact(id)
                .build();
        return handleCommand(deleteUserCommand);
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody RequestContactDto requestContactDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        } else {

            Contact contact = new Contact.Builder()
                    .username(requestContactDto.username())
                    .email(requestContactDto.email())
                    .password(requestContactDto.password())
                    .phoneNumber(requestContactDto.phoneNumber())
                    .build();
            UpdateUserCommand updateUserCommand=UpdateUserCommand.builder()
                    .email(contact.getEmail())
                    .idContact(contact.getIdContact())
                    .password(contact.getPassword())
                    .phoneNumber(contact.getPhoneNumber())
                    .profileImage(contact.getProfileImage())
                    .build();

                return handleCommand(updateUserCommand);
        }
    }
    private ResponseEntity<?> handleCommand(Object command) {
    try {
        senderCommandService.send(command);
        return ResponseEntity.ok(Map.of("message", "Operation executed successfully"));
    } catch (Exception e) {
        return ResponseEntity.internalServerError()
            .body(Map.of("error", "Operation failed: " + e.getMessage()));
    }
}
}
