package com.nagarro.microFrontend.opt.exception;

public class FailedToSendException extends RuntimeException{
    public FailedToSendException(String message){
        super(message);
    }
}
