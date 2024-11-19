package com.coolers.cloud.cloudGateway.config;

import com.alibaba.fastjson.JSON;
import com.coolers.cloud.cloudGateway.constant.FormatConst;
import com.coolers.cloud.cloudGateway.constant.RetMsgConst;
import com.coolers.cloud.cloudGateway.constant.RetTypeConst;
import com.coolers.cloud.cloudGateway.dto.BussinessException;
import com.coolers.cloud.cloudGateway.util.BaseUtil;
import com.coolers.cloud.cloudGateway.vo.BaseRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

@Component
public class GlobalExceptionResolver extends DefaultErrorAttributes {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ContentCachingRequestWrapper reqWrapper = (ContentCachingRequestWrapper) request;
        logger.error("user " + BaseUtil.getUserIdByBody(reqWrapper.getContentAsString()) + " request failure", ex);
        BaseRes res = buildRes(ex);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(FormatConst.UTF8);
        try {
            response.getWriter().write(JSON.toJSONString(res));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView();
    }

    private BaseRes buildRes(Exception ex) {
        BaseRes res = new BaseRes();
        if (ex instanceof BussinessException) {
            res.setType(((BussinessException) ex).getErrorType());
            res.setMsg(((BussinessException) ex).getErrorMessage());
        } else {
            res.setType(RetTypeConst.SYSTEM_ERR);
            res.setMsg(RetMsgConst.SYS_ERR);
        }
        return res;
    }

}
