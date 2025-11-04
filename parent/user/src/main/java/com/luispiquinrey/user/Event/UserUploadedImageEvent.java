package com.luispiquinrey.user.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUploadedImageEvent {
    private String username;
    private String key;
    private String imageUrl;
}
