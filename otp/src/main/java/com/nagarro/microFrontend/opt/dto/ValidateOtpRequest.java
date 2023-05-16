package com.nagarro.microFrontend.opt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOtpRequest {
    private String otp;
    private String phoneNumber;
}
