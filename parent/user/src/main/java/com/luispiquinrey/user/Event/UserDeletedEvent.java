package com.luispiquinrey.user.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDeletedEvent {
    private String username;
}
