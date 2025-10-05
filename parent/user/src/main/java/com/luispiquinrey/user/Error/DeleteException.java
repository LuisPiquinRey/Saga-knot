package com.luispiquinrey.User.Error;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DeleteException extends RuntimeException {
    
    private final String timestamp;
    private final Long contactId;
    private final String deleteType;
    private final String operation;
    
    public DeleteException(String message) {
        this(message, null, null, null, null);
    }
    
    public DeleteException(String message, Long contactId) {
        this(message, null, contactId, null, null);
    }
    
    public DeleteException(String message, Throwable cause) {
        this(message, cause, null, null, null);
    }
    
    public DeleteException(String message, Throwable cause, Long contactId) {
        this(message, cause, contactId, null, null);
    }
    
    public DeleteException(String message, Throwable cause, Long contactId, String deleteType, String operation) {
        super(message, cause);
        this.timestamp = DateTimeFormatter.ISO_INSTANT
                        .format(Instant.now().atOffset(ZoneOffset.UTC));
        this.contactId = contactId;
        this.deleteType = deleteType;
        this.operation = operation;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append("🗑️ [").append(timestamp).append("] DELETE ERROR: ");
        
        if (operation != null) {
            msg.append("[").append(operation).append("] ");
        }
        
        if (deleteType != null) {
            msg.append("[").append(deleteType).append("] ");
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
    
    public String getDeleteType() {
        return deleteType;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public static DeleteException notFound(String username) {
        return new DeleteException("User not found for deletion with username: " + username, null, null, "NOT_FOUND", "deleteByUsername");
    }
    
    public static DeleteException notFoundById(Long id) {
        return new DeleteException("User not found for deletion", null, id, "NOT_FOUND", "deleteById");
    }
    
    public static DeleteException notFoundByEmail(String email) {
        return new DeleteException("User not found for deletion with email: " + email, null, null, "NOT_FOUND", "deleteByEmail");
    }
    
    public static DeleteException constraintViolation(String username, Throwable cause) {
        return new DeleteException("Cannot delete user due to foreign key constraints", cause, null, "CONSTRAINT_VIOLATION", "deleteByUsername");
    }
    
    public static DeleteException adminDeletion(String username) {
        return new DeleteException("Cannot delete admin user: " + username, null, null, "ADMIN_PROTECTION", "deleteByUsername");
    }
    
    public static DeleteException databaseError(String operation, Throwable cause) {
        return new DeleteException("Database error during delete operation", cause, null, "DB_ERROR", operation);
    }
    
    public static DeleteException concurrentModification(Long contactId) {
        return new DeleteException("User was modified by another process", null, contactId, "CONCURRENT_MODIFICATION", "delete");
    }
}