package com.luispiquinrey.user.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.user.Command.CreateUserCommand;
import com.luispiquinrey.user.Repository.ContactLookupRepository;

@Component
public class CommandInterceptorUser implements MessageDispatchInterceptor<Message<?>> {

    private final ContactLookupRepository contactLookupRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Autowired
    public CommandInterceptorUser(ContactLookupRepository contactLookupRepository) {
        this.contactLookupRepository = contactLookupRepository;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            if (CreateUserCommand.class.equals(command.getPayloadType())) {
                CreateUserCommand createUserCommand = (CreateUserCommand) command.getPayload();

                if (createUserCommand.getUsername() == null || createUserCommand.getUsername().isBlank()) {
                    throw new IllegalArgumentException("Username cannot be null or empty");
                }
                if (createUserCommand.getUsername().length() < 3) {
                    throw new IllegalArgumentException("Username must be at least 3 characters long");
                }
                if (createUserCommand.getEmail() == null || createUserCommand.getEmail().isBlank()) {
                    throw new IllegalArgumentException("Email cannot be null or empty");
                }
                if (!EMAIL_PATTERN.matcher(createUserCommand.getEmail()).matches()) {
                    throw new IllegalArgumentException("Email format is invalid");
                }
                if (createUserCommand.getPassword() == null || createUserCommand.getPassword().isBlank()) {
                    throw new IllegalArgumentException("Password cannot be null or empty");
                }
                if (createUserCommand.getPassword().length() < 6) {
                    throw new IllegalArgumentException("Password must be at least 6 characters long");
                }
                if (contactLookupRepository.findByUsername(createUserCommand.getUsername()).isPresent()) {
                    throw new IllegalStateException("User already exists in database");
                }
            }
            return command;
        };
    }
}
