package com.luispiquinrey.user.Projection;

import java.io.IOException;
import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.luispiquinrey.Error.UpdateException;
import com.luispiquinrey.user.Entities.Contact;
import com.luispiquinrey.user.Event.UserCreatedEvent;
import com.luispiquinrey.user.Event.UserDeletedEvent;
import com.luispiquinrey.user.Event.UserUpdatedEvent;
import com.luispiquinrey.user.Event.UserUploadedImageEvent;
import com.luispiquinrey.user.Service.ContactService;
import com.luispiquinrey.user.Service.S3CloudService;

@Component
@ProcessingGroup("user-group")
public class UserProjection {

    private final ContactService contactService;
    private final S3CloudService s3CloudService;

    @Autowired
    public UserProjection(ContactService contactService,
            S3CloudService s3CloudService) {
        this.contactService = contactService;
        this.s3CloudService = s3CloudService;
    }

    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(userCreatedEvent, contact);
        contactService.createTarget(contact);
    }

    @EventHandler
    public void on(UserDeletedEvent userDeletedEvent) {
        contactService.deleteByUsername(userDeletedEvent.getUsername());
    }

    @EventHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        Optional<Contact> contactOpt = contactService.findByUsername(userUpdatedEvent.getUsername());
        if (!contactOpt.isPresent()) {
            throw new IllegalStateException("No user found with username: " + userUpdatedEvent.getUsername());
        }
        Contact contact = contactOpt.get();
        BeanUtils.copyProperties(userUpdatedEvent, contact);
        contactService.updateTarget(contact);
    }

    @EventHandler
    public void on(UserUploadedImageEvent userUploadedImageEvent) throws IOException {
        String key = userUploadedImageEvent.getKey();
        MultipartFile image = userUploadedImageEvent.getImage();
        Optional<Contact> contactOpt = contactService.findByUsername(userUploadedImageEvent.getUsername());
        if (!contactOpt.isPresent()) {
            throw new IllegalStateException("No user found with username: " + userUploadedImageEvent.getUsername());
        }
        if (!isValidImageFile(image)) {
            throw new IllegalArgumentException("Invalid image type: " + image.getContentType());
        }
        String imageUrl = s3CloudService.fileExists(key)
                ? s3CloudService.getFileUrl(key)
                : s3CloudService.uploadFile(key, image);
        Contact contact = contactOpt.get();
        contact.setProfileImage(imageUrl);
        try {
            contactService.updateTarget(contact);
        } catch (UpdateException e) {
            throw new RuntimeException("Failed to update contact with image URL", e);
        }
    }

    private boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg")
                || contentType.equals("image/jpg")
                || contentType.equals("image/png")
                || contentType.equals("image/gif")
                || contentType.equals("image/webp"));
    }
}
