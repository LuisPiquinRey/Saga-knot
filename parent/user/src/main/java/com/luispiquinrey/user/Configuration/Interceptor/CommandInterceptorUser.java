package com.luispiquinrey.user.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.user.Command.AddAddressToUserCommand;
import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Command.DeleteUserCommand;
import com.luispiquinrey.user.Command.RemoveAddressFromUserCommand;
import com.luispiquinrey.user.Command.UpdateUserCommand;
import com.luispiquinrey.user.Command.UploadImageUserCommand;
import com.luispiquinrey.user.Repository.AddressLookupRepository;
import com.luispiquinrey.user.Repository.ContactLookupRepository;

@Component
public class CommandInterceptorUser implements MessageDispatchInterceptor<Message<?>> {

    private final ContactLookupRepository contactLookupRepository;
    private final AddressLookupRepository addressLookupRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");

    @Autowired
    public CommandInterceptorUser(ContactLookupRepository contactLookupRepository,
            AddressLookupRepository addressLookupRepository) {
        this.contactLookupRepository = contactLookupRepository;
        this.addressLookupRepository = addressLookupRepository;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            Object payload = command.getPayload();

            if (payload instanceof CreateUserCommand createUser) {
                validateCreateUserCommand(createUser);
            } else if (payload instanceof UpdateUserCommand updateUser) {
                validateUpdateUserCommand(updateUser);
            } else if (payload instanceof DeleteUserCommand deleteUser) {
                validateDeleteUserCommand(deleteUser);
            } else if (payload instanceof UploadImageUserCommand uploadImage) {
                validateUploadImageUserCommand(uploadImage);
            } else if (payload instanceof AddAddressToUserCommand addAddress) {
                validateAddAddressToUserCommand(addAddress);
            } else if (payload instanceof RemoveAddressFromUserCommand removeAddress) {
                validateRemoveAddressFromUserCommand(removeAddress);
            }

            return command;
        };
    }

    private void validateCreateUserCommand(CreateUserCommand command) {
        validateUsername(command.getUsername());
        validateEmail(command.getEmail());
        validatePassword(command.getPassword());

        if (command.getPhoneNumber() != null && !command.getPhoneNumber().isBlank()) {
            validatePhoneNumber(command.getPhoneNumber());
        }
        if (contactLookupRepository.findByUsername(command.getUsername()).isPresent()) {
            throw new IllegalStateException("Username '" + command.getUsername() + "' already exists");
        }
        if (contactLookupRepository.findByEmail(command.getEmail()).isPresent()) {
            throw new IllegalStateException("Email '" + command.getEmail() + "' already exists");
        }
    }

    private void validateUpdateUserCommand(UpdateUserCommand command) {
        validateUsername(command.getUsername());
        if (contactLookupRepository.findByUsername(command.getUsername()).isEmpty()) {
            throw new IllegalStateException("User '" + command.getUsername() + "' does not exist");
        }
        if (command.getEmail() != null && !command.getEmail().isBlank()) {
            validateEmail(command.getEmail());
        }
        if (command.getPassword() != null && !command.getPassword().isBlank()) {
            validatePassword(command.getPassword());
        }
        if (command.getPhoneNumber() != null && !command.getPhoneNumber().isBlank()) {
            validatePhoneNumber(command.getPhoneNumber());
        }
    }

    private void validateDeleteUserCommand(DeleteUserCommand command) {
        validateUsername(command.getUsername());

        if (contactLookupRepository.findByUsername(command.getUsername()).isEmpty()) {
            throw new IllegalStateException("User '" + command.getUsername() + "' does not exist");
        }
    }

    private void validateUploadImageUserCommand(UploadImageUserCommand command) {
        validateUsername(command.getUsername());

        if (contactLookupRepository.findByUsername(command.getUsername()).isEmpty()) {
            throw new IllegalStateException("User '" + command.getUsername() + "' does not exist");
        }
        if (command.getImageUrl() == null || command.getImageUrl().isBlank()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty");
        }
        if (command.getKey() == null || command.getKey().isBlank()) {
            throw new IllegalArgumentException("Image key cannot be null or empty");
        }
    }

    private void validateAddAddressToUserCommand(AddAddressToUserCommand command) {
        validateUsername(command.getUsername());

        if (contactLookupRepository.findByUsername(command.getUsername()).isEmpty()) {
            throw new IllegalStateException("User '" + command.getUsername() + "' does not exist");
        }

        validateAddressFields(command.getStreet(), command.getCity(), command.getState(),
                command.getCountry(), command.getPostalCode());
    }

    private void validateRemoveAddressFromUserCommand(RemoveAddressFromUserCommand command) {
        if (command.getIdAddress() == null || command.getIdAddress().isBlank()) {
            throw new IllegalArgumentException("Address ID cannot be null or empty");
        }
        if (addressLookupRepository.findById(command.getIdAddress()).isEmpty()) {
            throw new IllegalStateException("Address with ID '" + command.getIdAddress() + "' does not exist");
        }
    }

    // Validation helper methods
    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
        if (username.length() > 50) {
            throw new IllegalArgumentException("Username cannot exceed 50 characters");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email format is invalid");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(
                    "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&)"
            );
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Phone number must be in international format (E.164)");
        }
    }

    private void validateAddressFields(String street, String city, String state, String country, String postalCode) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (street.length() < 3 || street.length() > 100) {
            throw new IllegalArgumentException("Street must be between 3 and 100 characters");
        }

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (city.length() < 2 || city.length() > 50) {
            throw new IllegalArgumentException("City must be between 2 and 50 characters");
        }

        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        if (state.length() < 2 || state.length() > 50) {
            throw new IllegalArgumentException("State must be between 2 and 50 characters");
        }

        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        if (country.length() < 2 || country.length() > 50) {
            throw new IllegalArgumentException("Country must be between 2 and 50 characters");
        }

        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }
        if (!POSTAL_CODE_PATTERN.matcher(postalCode).matches()) {
            throw new IllegalArgumentException("Invalid postal code format (expected: 12345 or 12345-6789)");
        }
    }
}
