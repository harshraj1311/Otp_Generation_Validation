package com.nagarro.microFrontend.opt.repository;

import com.nagarro.microFrontend.opt.entity.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface OtpRepository extends JpaRepository<OneTimePassword, Long> {
    Optional<OneTimePassword> findByPhoneNumber(String phoneNumber);
    Optional<OneTimePassword> findByPhoneNumberAndOtp(String phoneNumber, String otp);
}
