package com.coolers.cloud.cloudGateway.vo;

import lombok.Data;

@Data
public class BaseReq {
    private String userId;
    private Integer pageSize;
    private Integer pageNum;
}
