package com.coolers.cloud.cloudGateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BussinessException extends RuntimeException {
    private String errorType;
    private String errorMessage;
}
