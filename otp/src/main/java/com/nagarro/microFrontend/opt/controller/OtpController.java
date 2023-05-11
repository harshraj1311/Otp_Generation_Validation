package com.nagarro.microFrontend.opt.controller;

import com.nagarro.microFrontend.opt.constant.Constants;
import com.nagarro.microFrontend.opt.dto.OtpResponse;
import com.nagarro.microFrontend.opt.dto.SendOtpRequest;
import com.nagarro.microFrontend.opt.dto.ValidateOtpRequest;
import com.nagarro.microFrontend.opt.service.SendingOtpService;
import com.nagarro.microFrontend.opt.service.ValidateOtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
//@Api(value = "OTPController", tags = "Controller")
public class OtpController {
    @Autowired
    private SendingOtpService sendingOtpService;
    @Autowired
    private ValidateOtpService validateOtpService;

    @PostMapping("/generate")
    @ApiOperation(value = "Send OTP", notes = "send otp to user's mobile number")
    public ResponseEntity<OtpResponse> sendOtp(@RequestBody SendOtpRequest sendOtpRequest) throws Exception {
        OtpResponse otpResponse = sendingOtpService.sendOTPForMobileNumberVerification(sendOtpRequest.getPhoneNumber());
        return new ResponseEntity<>(otpResponse, HttpStatus.valueOf(otpResponse.getStatusCode()));
    }

    @PostMapping("/validate")
    @ApiOperation(value = "Validation of OTP", notes = "validate otp")
    public ResponseEntity<OtpResponse> validateOtp(@RequestBody ValidateOtpRequest validateOtpRequest) {
        validateOtpService.validateOtp(validateOtpRequest);
        return new ResponseEntity<>(new OtpResponse(null, HttpStatus.OK.value(), Constants.OTP_VALID, null), HttpStatus.OK);
    }
}
