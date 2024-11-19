package com.coolers.cloud.cloudGateway.config;

import com.coolers.cloud.cloudGateway.constant.BaseConst;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 限流配置KeyResolver——有三种写法(接口限流/ip限流/用户限流)
 */
@Configuration
public class RateLimiteConfig {

    /**
     * 接口限流：根据请求路径限流
     * 如果不使用@Primary注解，会报错
     */
    @Bean
    @Primary
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

    /**
     * 根据请求IP限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
    }

    /**
     * 根据请求参数中的userId进行限流
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst(BaseConst.USER_ID_KEY))
        );
    }
}