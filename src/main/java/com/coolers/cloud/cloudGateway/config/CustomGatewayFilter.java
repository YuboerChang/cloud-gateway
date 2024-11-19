package com.coolers.cloud.cloudGateway.config;

import com.coolers.cloud.cloudGateway.dao.WebLogMapper;
import com.coolers.cloud.cloudGateway.po.WebLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;

@Component
public class CustomGatewayFilter extends AbstractGatewayFilterFactory {

    @Autowired
    WebLogMapper webLogMapper;

    @Override
    public org.springframework.cloud.gateway.filter.GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpRequest request = exchange.getRequest();
                ServerHttpResponse response = exchange.getResponse();
                // 这里可以将日志数据保存到数据库
                saveLogToDatabase(request, response);
            }));
        };
    }

    /**
     * 日志记录
     */
    public void saveLogToDatabase(ServerHttpRequest request, ServerHttpResponse response) {
        WebLog webLog = new WebLog();
        webLog.setIp(Objects.requireNonNull(request.getRemoteAddress()).toString());
        webLog.setReqParams(request.getBody().toString());
        webLog.setCreateTime(new Date());
        webLogMapper.insert(webLog);
    }
}
