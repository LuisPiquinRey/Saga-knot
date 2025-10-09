package com.luispiquinrey.user.Event;

import lombok.Data;

@Data
public class UserUpdatedEvent {
    private Long idContact;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImage;
}
