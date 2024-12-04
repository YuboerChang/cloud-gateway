package com.coolers.cloud.cloudGateway.share;

import com.coolers.cloud.cloudShare.dto.BusinessException;
import com.coolers.cloud.cloudGateway.vo.BaseErrorRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        HttpStatusCode statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof BusinessException) {
            statusCode = HttpStatus.FORBIDDEN;
        }
        BaseErrorRes errorResponse = new BaseErrorRes(
                statusCode.value(),
                ex.getMessage(),
                request.getContextPath()
        );
        return new ResponseEntity<>(errorResponse, statusCode);
    }

}
