package com.nagarro.microFrontend.opt.service.impl;

import com.nagarro.microFrontend.opt.config.SendOtpConfig;
import com.nagarro.microFrontend.opt.constant.Constants;
import com.nagarro.microFrontend.opt.dto.OtpResponse;
import com.nagarro.microFrontend.opt.entity.OneTimePassword;
import com.nagarro.microFrontend.opt.exception.FailedToSendException;
import com.nagarro.microFrontend.opt.repository.OtpRepository;
import com.nagarro.microFrontend.opt.service.SendingOtpService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class SendingOtpServiceImpl implements SendingOtpService {
    @Autowired
    private SendOtpConfig sendOtpConfig;
    @Autowired
    private OtpRepository otpRepository;

    /*
     * sends otp for phone number verification
     *@Param number
     *@return Otp response with statuscode, message if it is delivered then success else bad request
     *
     * */
    @Override
    public OtpResponse sendOTPForMobileNumberVerification(String number) throws FailedToSendException {
            final String generatedOTP = generateOTP();
            sendOtp(number, generatedOTP);
            final OtpResponse otpResponse = new OtpResponse(null, HttpStatus.SC_OK, Constants.OTP_DELIVERED_MESSAGE, null);
            saveOtp(number, generatedOTP);
        return otpResponse;
    }

    /*
     * to generate a 6 digit otp
     */
    private String generateOTP() {
        return new DecimalFormat(Constants.OTP_DECIMAL_FORMAT).format(new Random().nextInt(Constants.INT_BOUND));
    }

    /*
     * to send OTP to a specific phone number
     * @param1 number
     * @param2 otp
     * @return boolean value true if send else throw exception
     * */
    private void sendOtp(String number, String otp) throws FailedToSendException {
        try {
            final PhoneNumber to = new PhoneNumber(number);
            final PhoneNumber from = new PhoneNumber(sendOtpConfig.getTrialNumber());
            final String otpMessage = "Dear Customer, Your OTP for mobile verification is " + otp + " . It will expire in 2 minutes.";
            Message.creator(to, from, otpMessage).create();
        } catch (Exception ex) {
            throw new FailedToSendException(ex.getMessage());
        }
    }

    /*
     * to save otp to database with expiry time
     * @param1 phoneNumber
     * @param2 otp
     */
    private void saveOtp(String phoneNumber, String otp) {
        final Optional<OneTimePassword> optionalOneTimePassword = otpRepository.findByPhoneNumber(phoneNumber);
        OneTimePassword otpEntity = null;
        otpEntity = optionalOneTimePassword.orElseGet(OneTimePassword::new);
        otpEntity.setPhoneNumber(phoneNumber);
        otpEntity.setOtp(otp);
        Instant expiryTime = Instant.now().plus(Constants.EXPIRE_TIME_MINUTES, ChronoUnit.MINUTES);
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_INSTANT;
        otpEntity.setExpiryTime(isoFormatter.format(expiryTime));
        otpRepository.save(otpEntity);
    }
}
