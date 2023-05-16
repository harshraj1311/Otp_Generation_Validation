package com.nagarro.microFrontend.opt.service.impl;

import com.nagarro.microFrontend.opt.constant.Constants;
import com.nagarro.microFrontend.opt.dto.ValidateOtpRequest;
import com.nagarro.microFrontend.opt.entity.OneTimePassword;
import com.nagarro.microFrontend.opt.exception.OtpValidationFailedException;
import com.nagarro.microFrontend.opt.repository.OtpRepository;
import com.nagarro.microFrontend.opt.service.ValidateOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ValidateOtpServiceImpl implements ValidateOtpService {
    @Autowired
    private OtpRepository otpRepository;

    /*
     * validates the one-time password associated with a phone number provided
     * @param otpRequest consisting of phone
     * @return true if valid or else false
     * */
    @Override
    public void validateOtp(ValidateOtpRequest validateOtpRequest) {
        final Optional<OneTimePassword> otpEntityOptional = otpRepository.findByPhoneNumberAndOtp(validateOtpRequest.getPhoneNumber(), validateOtpRequest.getOtp());
        if (otpEntityOptional.isPresent()) {
            final OneTimePassword otpEntity = otpEntityOptional.get();
            if (Instant.parse(otpEntity.getExpiryTime()).isAfter(Instant.now())) {
                return;
            } else {
                otpRepository.delete(otpEntity);
                throw new OtpValidationFailedException(Constants.OTP_EXPIRED_MESSAGE);
            }
        }
        else {
            throw new OtpValidationFailedException(Constants.OTP_INVALID);
        }
    }
}
