package com.nagarro.microFrontend.opt.config;

import com.twilio.Twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class Config {
    @Autowired
    private SendOtpConfig sendOtpConfig;

    @PostConstruct
    public void initSendingOpt(){
        Twilio.init(sendOtpConfig.getAccountSid(), sendOtpConfig.getAuthToken());
    }
}
