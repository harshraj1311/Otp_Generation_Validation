package com.nagarro.microFrontend.opt.dto;

import lombok.Data;

@Data
public class SendOtpRequest {
    private String countryCode;
    private String mobileNumber;
}
