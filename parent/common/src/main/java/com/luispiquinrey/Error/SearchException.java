package com.luispiquinrey.Error;

public class SearchException extends RuntimeException{
    public SearchException(){
        super();
    }
    public SearchException(String message){
        super(message);
    }
    public SearchException(Throwable cause) {
        super(cause);
    }
}
