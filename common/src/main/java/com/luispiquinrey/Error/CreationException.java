package com.luispiquinrey.Error;

public class CreationException extends RuntimeException{
    public CreationException(){
        super();
    }
    public CreationException(String message){
        super(message);
    }
    public CreationException(Throwable cause) {
        super(cause);
    }
}
