package com.luispiquinrey.user.Error;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CountException extends RuntimeException {
    
    private final String timestamp;
    private final String countType;
    private final String operation;
    
    public CountException(String message) {
        this(message, null, null, null);
    }
    
    public CountException(String message, String countType) {
        this(message, null, countType, null);
    }
    
    // Constructor con causa
    public CountException(String message, Throwable cause) {
        this(message, cause, null, null);
    }

    public CountException(String message, Throwable cause, String countType) {
        this(message, cause, countType, null);
    }
    
    public CountException(String message, Throwable cause, String countType, String operation) {
        super(message, cause);
        this.timestamp = DateTimeFormatter.ISO_INSTANT
                        .format(Instant.now().atOffset(ZoneOffset.UTC));
        this.countType = countType;
        this.operation = operation;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append("🔢 [").append(timestamp).append("] COUNT ERROR: ");
        
        if (operation != null) {
            msg.append("[").append(operation).append("] ");
        }
        
        if (countType != null) {
            msg.append("[").append(countType).append("] ");
        }
        
        msg.append(super.getMessage());
        
        return msg.toString();
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public String getCountType() {
        return countType;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public static CountException adminCountFailed(Throwable cause) {
        return new CountException("Failed to count admin users", cause, "ADMIN_COUNT", "countAdmins");
    }
    
    public static CountException userCountFailed(Throwable cause) {
        return new CountException("Failed to count total users", cause, "USER_COUNT", "countUsers");
    }
    
    public static CountException databaseConnectionError(String operation) {
        return new CountException("Database connection failed during count operation", null, "DB_ERROR", operation);
    }
    
    public static CountException timeoutError(String operation) {
        return new CountException("Count operation timed out", null, "TIMEOUT", operation);
    }
    
    public static CountException invalidResult(String operation, long result) {
        return new CountException("Invalid count result: " + result, null, "INVALID_RESULT", operation);
    }
}