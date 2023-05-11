package com.nagarro.microFrontend.opt.service;

import com.nagarro.microFrontend.opt.dto.ValidateOtpRequest;

public interface ValidateOtpService {
    /*
     * validates the one-time password associated with a phone number provided
     * @param otpRequest consisting of phone
     * @return true if valid or else false
     * */
    public void validateOtp(ValidateOtpRequest validateOtpRequest);
}
