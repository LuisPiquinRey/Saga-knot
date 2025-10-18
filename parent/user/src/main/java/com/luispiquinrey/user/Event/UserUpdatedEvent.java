package com.luispiquinrey.user.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdatedEvent {
    private Long idContact;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImage;
}
