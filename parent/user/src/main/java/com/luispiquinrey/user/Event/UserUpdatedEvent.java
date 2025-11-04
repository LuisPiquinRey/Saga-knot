package com.luispiquinrey.user.Event;

import java.util.HashMap;

import com.luispiquinrey.user.Entities.Address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdatedEvent {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private HashMap<String,Address> addresses;
}
