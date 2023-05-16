package com.nagarro.microFrontend.opt.serviceTest;

import com.nagarro.microFrontend.opt.constant.Constants;
import com.nagarro.microFrontend.opt.dto.ValidateOtpRequest;
import com.nagarro.microFrontend.opt.entity.OneTimePassword;
import com.nagarro.microFrontend.opt.exception.OtpValidationFailedException;
import com.nagarro.microFrontend.opt.repository.OtpRepository;
import com.nagarro.microFrontend.opt.service.impl.ValidateOtpServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidateOtpServiceTest {
    @Mock
    private ValidateOtpServiceImpl validateOtpService;

    @Mock
    private OtpRepository otpRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void validateOtp_validOtp_success() {
        final String phoneNumber = "+1234567890";
        final String otp = "123456";
        final Optional<OneTimePassword> optionalOneTimePassword = Optional.of(new OneTimePassword(1L, phoneNumber, otp, Instant.now().plusSeconds(300).toString()));

        when(otpRepository.findByPhoneNumberAndOtp(phoneNumber, otp)).thenReturn(optionalOneTimePassword);

        final ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest(phoneNumber, otp);

        assertDoesNotThrow(() -> validateOtpService.validateOtp(validateOtpRequest));
    }
    @Test
    void validateOtp_invalidOtp_exceptionThrown() {
        final String phoneNumber = "+1234567890";
        final String otp = "123456";
        when(otpRepository.findByPhoneNumberAndOtp(phoneNumber, otp)).thenReturn(Optional.empty());

        final ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest(phoneNumber, otp);

        final OtpValidationFailedException exception = assertThrows(OtpValidationFailedException.class, () -> validateOtpService.validateOtp(validateOtpRequest));
        assertEquals(Constants.OTP_INVALID, exception.getMessage());
    }
    @Test
    void validateOtp_expiredOtp_exceptionThrown() {
        final String phoneNumber = "+1234567890";
        final String otp = "123456";
        final Optional<OneTimePassword> optionalOneTimePassword = Optional.of(new OneTimePassword(1L,phoneNumber, otp, Instant.now().minusSeconds(300).toString()));

        when(otpRepository.findByPhoneNumberAndOtp(phoneNumber, otp)).thenReturn(optionalOneTimePassword);

        final ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest(phoneNumber, otp);

//        final OtpValidationFailedException exception = assertThrows(OtpValidationFailedException.class, () -> validateOtpService.validateOtp(validateOtpRequest));
//        assertEquals(Constants.OTP_EXPIRED_MESSAGE, exception.getMessage());
        assertThrows(OtpValidationFailedException.class, () -> {
            validateOtpService.validateOtp(validateOtpRequest);
        });

        verify(otpRepository, times(1)).findByPhoneNumberAndOtp(validateOtpRequest.getPhoneNumber(), validateOtpRequest.getOtp());
    }
}
