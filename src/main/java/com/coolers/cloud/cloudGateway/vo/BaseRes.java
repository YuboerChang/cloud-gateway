package com.coolers.cloud.cloudGateway.vo;

import com.coolers.cloud.cloudShare.constant.BaseConst;
import lombok.Data;

@Data
public class BaseRes {
    private String type = BaseConst.SUCCESS;
    private String msg;
    private long total;
}
