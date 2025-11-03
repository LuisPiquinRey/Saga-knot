package com.luispiquinrey.user.Configuration.Interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.user.Command.CreateAddressCommand;
import com.luispiquinrey.user.Repository.AddressLookupRepository;

@Component
public class CommandInterceptorAddress implements MessageDispatchInterceptor<Message<?>> {

    private final AddressLookupRepository addressLookupRepository;

    @Autowired
    public CommandInterceptorAddress(AddressLookupRepository addressLookupRepository) {
        this.addressLookupRepository = addressLookupRepository;
    }

    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, command) -> {
            if (CreateAddressCommand.class.equals(command.getPayloadType())) {
                CreateAddressCommand createAddressCommand = (CreateAddressCommand) command.getPayload();

                if (createAddressCommand.getIdAddress() == null || createAddressCommand.getIdAddress().isEmpty()) {
                    throw new IllegalArgumentException("Address id cannot be null or empty");
                }
                if (createAddressCommand.getStreet() == null || createAddressCommand.getStreet().isBlank()) {
                    throw new IllegalArgumentException("Street cannot be null or empty");
                }
                if (createAddressCommand.getCity() == null || createAddressCommand.getCity().isBlank()) {
                    throw new IllegalArgumentException("City cannot be null or empty");
                }
                if (createAddressCommand.getCountry() == null || createAddressCommand.getCountry().isBlank()) {
                    throw new IllegalArgumentException("Country cannot be null or empty");
                }
                if (createAddressCommand.getIdContact() == null) {
                    throw new IllegalArgumentException("Contact id cannot be null");
                }
                if (addressLookupRepository.findById(createAddressCommand.getIdAddress()).isPresent()) {
                    throw new IllegalStateException("Address is already in database");
                }
            }
            return command;
        };
    }
}
