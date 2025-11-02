package com.luispiquinrey.user.Controller;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.Command.UploadImageUserCommand;
import com.luispiquinrey.user.DTOs.ContactDto;
import com.luispiquinrey.user.DTOs.UpdateContactDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/command")
public class UserController {

    private final CommandGateway commandGateway;

    @Autowired
    public UserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ContactDto requestContactDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        
        CreateUserCommand createUserCommand = CreateUserCommand.builder()
                .username(requestContactDto.username())
                .email(requestContactDto.email())
                .password(requestContactDto.password())
                .phoneNumber(requestContactDto.phoneNumber())
                .build();
        
        return handleCommand(createUserCommand, "User created successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        DeleteUserCommand deleteUserCommand = DeleteUserCommand.builder()
                .username(username)
                .build();
        return handleCommand(deleteUserCommand, "User deleted successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateContactDto updateContactDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        
        UpdateUserCommand updateUserCommand = UpdateUserCommand.builder()
                .email(updateContactDto.email())
                .password(updateContactDto.password())
                .phoneNumber(updateContactDto.phoneNumber())
                .build();

        return handleCommand(updateUserCommand, "User updated successfully");
    }

    @PostMapping("/image/{username}")
    public ResponseEntity<?> image(@PathVariable String username, @RequestParam("image") MultipartFile image) {
        UploadImageUserCommand uploadImageUserCommand = UploadImageUserCommand.builder()
                .image(image)
                .username(username)
                .build();
        return handleCommand(uploadImageUserCommand, "User image updated successfully");
    }

    private ResponseEntity<?> handleCommand(Object command, String message) {
        try {
            Object result = commandGateway.sendAndWait(command);
            return ResponseEntity.ok(Map.of(
                "message", message,
                "result", result
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Operation failed: " + e.getMessage()));
        }
    }
}