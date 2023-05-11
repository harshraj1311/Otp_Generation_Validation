package com.nagarro.microFrontend.opt.dto;

import lombok.Data;
@Data
public class ValidateOtpRequest {
    private String otp;
    private String phoneNumber;
}
