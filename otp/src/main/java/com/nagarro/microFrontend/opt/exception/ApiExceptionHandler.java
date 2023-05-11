package com.nagarro.microFrontend.opt.exception;

import com.nagarro.microFrontend.opt.constant.Constants;
import com.nagarro.microFrontend.opt.dto.OtpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(FailedToSendException.class)
    public ResponseEntity<OtpResponse> FailedToSendExceptionHandler(FailedToSendException failedToSendException){
        final OtpResponse otpResponse = new OtpResponse(null, HttpStatus.BAD_REQUEST.value(), failedToSendException.getMessage(),null);
        return new ResponseEntity<>(otpResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OtpValidationFailedException.class)
    public ResponseEntity<OtpResponse> OtpValidationFailedExceptionHandler(OtpValidationFailedException otpValidationFailedException){
        return new ResponseEntity<>(new OtpResponse(null, HttpStatus.BAD_REQUEST.value(), otpValidationFailedException.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
