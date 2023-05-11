package com.nagarro.microFrontend.opt.exception;

public class OtpValidationFailedException extends RuntimeException{
    public OtpValidationFailedException(String message){
        super(message);
    }
}
