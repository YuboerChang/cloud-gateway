package com.coolers.cloud.cloudGateway.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseErrorRes {
    private int statusCode;
    private String message;
    private String path;
}
