package com.coolers.cloud.cloudGateway.share;

import com.coolers.cloud.cloudGateway.dao.WebLogMapper;
import com.coolers.cloud.cloudGateway.po.WebLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;

@Component
public class CustomGatewayFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(CustomGatewayFilter.class);

    @Autowired
    WebLogMapper webLogMapper;

    @Autowired
    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFilter;

    @Override
    @Transactional
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        WebLog webLog = new WebLog();
        ServerHttpRequest request = exchange.getRequest();
        webLog.setId(request.getId());
        webLog.setApiId(request.getPath().toString());
        webLog.setIp(Objects.requireNonNull(request.getRemoteAddress()).toString());
        if (HttpMethod.GET.name().equals(request.getMethod().name())) {
            webLog.setReqParams(request.getQueryParams().toString());
        } else if (HttpMethod.POST.name().equals(request.getMethod().name())) {
//            ServerRequest sr = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
//            sr.bodyToMono(String.class).flatMap(v -> {
//                webLog.setReqParams(v);
//                return Mono.just(v);
//            }).subscribe();
        }
        return modifyResponseBodyFilter.apply(
                new ModifyResponseBodyGatewayFilterFactory.Config().setRewriteFunction(Object.class, Object.class, (swe, responseBody) -> {
                    try {
                        webLog.setResType(Objects.requireNonNull(swe.getResponse().getStatusCode()).toString());
                        webLog.setResParams(new ObjectMapper().writeValueAsString(responseBody));
                        webLog.setCreateTime(new Date());
                        webLogMapper.insert(webLog);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return Mono.just(responseBody);
                })).filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
