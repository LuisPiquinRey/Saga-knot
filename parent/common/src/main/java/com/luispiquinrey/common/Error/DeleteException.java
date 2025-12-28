package com.luispiquinrey.common.Error;

public class DeleteException extends RuntimeException {
    public DeleteException() {
        super();
    }

    public DeleteException(String message) {
        super(message);
    }
    public DeleteException(Throwable cause) {
        super(cause);
    }
}