package com.luispiquinrey.user.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luispiquinrey.Service.SenderCommandService;
import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.Command.UploadImageUserCommand;
import com.luispiquinrey.user.DTOs.RequestContactDto;
import com.luispiquinrey.user.DTOs.UpdateContactDto;
import com.luispiquinrey.user.Service.S3CloudService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/command")
public class UserController {

    private final SenderCommandService senderCommandService;

    @Autowired
    public UserController(SenderCommandService senderCommandService) {
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
            CreateUserCommand createUserCommand = CreateUserCommand.builder()
                    .username(requestContactDto.username())
                    .email(requestContactDto.email())
                    .password(requestContactDto.password())
                    .phoneNumber(requestContactDto.phoneNumber())
                    .build();
            return handleCommand(createUserCommand, "User created sucessfully");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        DeleteUserCommand deleteUserCommand = DeleteUserCommand.builder()
                .idContact(id)
                .build();
        return handleCommand(deleteUserCommand, "User deleted sucessfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateContactDto updateContactDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        } else {
            UpdateUserCommand updateUserCommand = UpdateUserCommand.builder()
                    .email(updateContactDto.email())
                    .password(updateContactDto.password())
                    .phoneNumber(updateContactDto.phoneNumber())
                    .build();

            return handleCommand(updateUserCommand, "User updated sucessfully");
        }
    }

    @PostMapping("/image/{username}")
    public ResponseEntity<?> image(@PathVariable String username,
            MultipartFile image) {
        UploadImageUserCommand uploadImageUserCommand=UploadImageUserCommand.builder()
                .image(image)
                .username(username)
                .build();
        return handleCommand(uploadImageUserCommand, "User image updated sucessfully");
    }
    

    private ResponseEntity<?> handleCommand(Object command, String message) {
        try {
            senderCommandService.send(command);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Operation failed: " + e.getMessage()));
        }
    }
}
