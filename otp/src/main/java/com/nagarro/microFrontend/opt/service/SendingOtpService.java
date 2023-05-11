package com.nagarro.microFrontend.opt.service;

import com.nagarro.microFrontend.opt.dto.OtpResponse;
import com.nagarro.microFrontend.opt.exception.FailedToSendException;

public interface SendingOtpService {
    /*
     * sends otp for phone number verification
     *@Param number
     *@return Otp response with statuscode, message if it is delivered then success else bad request
     *
     * */
    OtpResponse sendOTPForMobileNumberVerification(String number) throws FailedToSendException;
}
