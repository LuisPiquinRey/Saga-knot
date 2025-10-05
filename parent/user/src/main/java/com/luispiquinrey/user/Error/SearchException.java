package com.luispiquinrey.User.Error;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SearchException extends RuntimeException {
    
    private final String timestamp;
    private final Long contactId;
    private final String operation;
    
    public SearchException(String message) {
        this(message, null, null, null);
    }
    

    public SearchException(String message, Long contactId) {
        this(message, null, contactId, null);
    }
    
    public SearchException(String message, Throwable cause) {
        this(message, cause, null, null);
    }
    
    public SearchException(String message, Throwable cause, Long contactId) {
        this(message, cause, contactId, null);
    }
    
    public SearchException(String message, Throwable cause, Long contactId, String operation) {
        super(message, cause);
        this.timestamp = DateTimeFormatter.ISO_INSTANT
                        .format(Instant.now().atOffset(ZoneOffset.UTC));
        this.contactId = contactId;
        this.operation = operation;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append("🔍 [").append(timestamp).append("] SEARCH ERROR: ");
        
        if (operation != null) {
            msg.append("[").append(operation).append("] ");
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
    
    public String getOperation() {
        return operation;
    }
    
    public static SearchException notFound(String username) {
        return new SearchException("User not found with username: " + username, null, null, "findByUsername");
    }
    
    public static SearchException notFoundById(Long id) {
        return new SearchException("User not found", null, id, "findById");
    }
    
    public static SearchException notFoundByEmail(String email) {
        return new SearchException("User not found with email: " + email, null, null, "findByEmail");
    }
    
    public static SearchException databaseError(String operation, Throwable cause) {
        return new SearchException("Database error during search operation", cause, null, operation);
    }
    
    public static SearchException invalidParameter(String parameter, String value) {
        return new SearchException("Invalid parameter " + parameter + ": " + value, null, null, "validation");
    }
}