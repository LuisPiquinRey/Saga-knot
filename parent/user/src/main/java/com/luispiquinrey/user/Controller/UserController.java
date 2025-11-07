package com.luispiquinrey.user.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.user.Command.AddAddressToUserCommand;
import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.RemoveAddressFromUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.Command.UploadImageUserCommand;
import com.luispiquinrey.user.DTOs.AddressDto;
import com.luispiquinrey.user.DTOs.ContactDto;
import com.luispiquinrey.user.DTOs.UpdateContactDto;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Queries.FindUserByEmailQuery;
import com.luispiquinrey.user.Queries.FindUserByUsernameQuery;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user/command")
@Slf4j
public class UserController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public UserController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
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

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        log.info("Deleting user with username: {}", username);
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

        log.info("Updating user with username: {}", updateContactDto.username());
        UpdateUserCommand updateUserCommand = UpdateUserCommand.builder()
                .username(updateContactDto.username())
                .email(updateContactDto.email())
                .password(updateContactDto.password())
                .phoneNumber(updateContactDto.phoneNumber())
                .build();

        return handleCommand(updateUserCommand, "User updated successfully");
    }

    @PutMapping("/image/{username}")
    public ResponseEntity<?> uploadImage(
            @PathVariable String username,
            @RequestBody Map<String, String> imageData) {

        String imageUrl = imageData.get("imageUrl");
        String key = imageData.get("key");

        if (imageUrl == null || imageUrl.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "imageUrl is required"));
        }
        if (key == null || key.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "key is required"));
        }

        log.info("Uploading image for user: {}", username);
        UploadImageUserCommand uploadImageUserCommand = UploadImageUserCommand.builder()
                .username(username)
                .imageUrl(imageUrl)
                .key(key)
                .build();
        return handleCommand(uploadImageUserCommand, "User image updated successfully");
    }

    @PostMapping("/address/{username}")
    public ResponseEntity<?> addAddress(
            @PathVariable String username,
            @Valid @RequestBody AddressDto addressDto,
            BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        AddAddressToUserCommand command = AddAddressToUserCommand.builder()
                .username(username)
                .street(addressDto.street())
                .city(addressDto.city())
                .state(addressDto.state())
                .country(addressDto.country())
                .postalCode(addressDto.postalCode())
                .build();

        return handleCommand(command, "Address added successfully");
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> removeAddress(@PathVariable String addressId) {
        log.info("Removing address: {}", addressId);
        RemoveAddressFromUserCommand command = RemoveAddressFromUserCommand.builder()
                .idAddress(addressId)
                .build();
        return handleCommand(command, "Address removed successfully");
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        FindUserByUsernameQuery findUserByUsernameQuery = new FindUserByUsernameQuery(username);
        return handleQuery(findUserByUsernameQuery, username,ResponseTypes.optionalInstanceOf(Contact.class));
    }
    @GetMapping("/find/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        FindUserByEmailQuery findUserByEmailQuery =new FindUserByEmailQuery(email);
        return handleQuery(findUserByEmailQuery,email,ResponseTypes.optionalInstanceOf(Contact.class));
    }

    private ResponseEntity<?> handleCommand(Object command, String message) {
        try {
            Object result = commandGateway.sendAndWait(command);
            return ResponseEntity.ok(Map.of(
                    "message", message,
                    "result", result
            ));
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Command execution failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Operation failed: " + e.getMessage()));
        }
    }

    private <Q, R> ResponseEntity<?> handleQuery(Q query, String message, ResponseType<R> responseType) {
        try {
            R result = queryGateway.query(query, responseType).join();
            return ResponseEntity.ok(Map.of(
                    "result", result,
                    "message", message
            ));
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Query execution failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Operation failed: " + e.getMessage()));
        }
    }

}
