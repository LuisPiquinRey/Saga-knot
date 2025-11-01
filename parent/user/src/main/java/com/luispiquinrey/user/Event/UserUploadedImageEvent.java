package com.luispiquinrey.user.Event;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUploadedImageEvent {
    private String username;
    private String key;
    private MultipartFile image;
}
