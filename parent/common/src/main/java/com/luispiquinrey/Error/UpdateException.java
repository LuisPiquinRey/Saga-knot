package com.luispiquinrey.Error;

public class UpdateException extends RuntimeException{
    public UpdateException(){
        super();
    }
    public UpdateException(String message){
        super(message);
    }
    public UpdateException(Throwable cause) {
        super(cause);
    }
}
