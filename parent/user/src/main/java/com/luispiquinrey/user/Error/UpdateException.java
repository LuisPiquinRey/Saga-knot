package com.luispiquinrey.user.Error;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class UpdateException extends RuntimeException {
    
    private final String timestamp;
    private final Long contactId;
    private final String updateType;
    private final String operation;
    private final String fieldName;
    
    public UpdateException(String message) {
        this(message, null, null, null, null, null);
    }
    
    public UpdateException(String message, Long contactId) {
        this(message, null, contactId, null, null, null);
    }
    
    public UpdateException(String message, Throwable cause) {
        this(message, cause, null, null, null, null);
    }
    
    public UpdateException(String message, Throwable cause, Long contactId) {
        this(message, cause, contactId, null, null, null);
    }
    
    public UpdateException(String message, Throwable cause, Long contactId, String updateType, String operation, String fieldName) {
        super(message, cause);
        this.timestamp = DateTimeFormatter.ISO_INSTANT
                        .format(Instant.now().atOffset(ZoneOffset.UTC));
        this.contactId = contactId;
        this.updateType = updateType;
        this.operation = operation;
        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append("📝 [").append(timestamp).append("] UPDATE ERROR: ");
        
        if (operation != null) {
            msg.append("[").append(operation).append("] ");
        }
        
        if (updateType != null) {
            msg.append("[").append(updateType).append("] ");
        }
        
        if (fieldName != null) {
            msg.append("[").append(fieldName).append("] ");
        }
        
        msg.append(super.getMessage());
        
        if (contactId != null) {
            msg.append(" (Contact ID: ").append(contactId).append(")");
        }
        
        return msg.toString();
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public Long getContactId() {
        return contactId;
    }
    
    public String getUpdateType() {
        return updateType;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public static UpdateException notFound(Long contactId) {
        return new UpdateException("User not found for update", null, contactId, "NOT_FOUND", "update", null);
    }
    
    public static UpdateException duplicateUsername(String username, Long contactId) {
        return new UpdateException("Username already exists: " + username, null, contactId, "DUPLICATE", "update", "username");
    }
    
    public static UpdateException duplicateEmail(String email, Long contactId) {
        return new UpdateException("Email already exists: " + email, null, contactId, "DUPLICATE", "update", "email");
    }
    
    public static UpdateException invalidField(String fieldName, String value, Long contactId) {
        return new UpdateException("Invalid value for field: " + value, null, contactId, "VALIDATION", "update", fieldName);
    }
    
    public static UpdateException nullField(String fieldName, Long contactId) {
        return new UpdateException("Required field cannot be null", null, contactId, "NULL_FIELD", "update", fieldName);
    }
    
    public static UpdateException optimisticLocking(Long contactId) {
        return new UpdateException("User was modified by another process", null, contactId, "OPTIMISTIC_LOCK", "update", null);
    }
    
    public static UpdateException adminStatusChange(Long contactId, Throwable cause) {
        return new UpdateException("Failed to update admin status", cause, contactId, "ADMIN_STATUS", "updateAdminStatus", "isAdmin");
    }
    
    public static UpdateException databaseError(String operation, Throwable cause) {
        return new UpdateException("Database error during update operation", cause, null, "DB_ERROR", operation, null);
    }
    
    public static UpdateException constraintViolation(String constraint, Long contactId, Throwable cause) {
        return new UpdateException("Database constraint violation: " + constraint, cause, contactId, "CONSTRAINT", "update", null);
    }
}