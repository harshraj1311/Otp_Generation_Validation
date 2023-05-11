package com.nagarro.microFrontend.opt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
@ConfigurationProperties(prefix = "otpsending")
public class SendOtpConfig {
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
