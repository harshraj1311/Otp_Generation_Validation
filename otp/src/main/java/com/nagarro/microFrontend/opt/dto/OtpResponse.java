package com.nagarro.microFrontend.opt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponse {
    private Long id;
    private int statusCode;
    private String message;
    private Object data;
}
