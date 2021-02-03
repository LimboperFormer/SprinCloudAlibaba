package com.lee.config.filters.globalFilter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器（实现统一鉴权）
 * 必须实现GlobalFilter，Ordered ，并且实现里面的2个方法
 *
 * @author lee
 * @version 1.0
 * @date 2021/2/2
 */
//@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 同意鉴权逻辑
     *
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (!StringUtils.equals("admin", token)) {
            //认证失败
            logger.info("认证失败。。。");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * 标识当前过滤器的优先级，返回值越小，优先级越高
     *
     * @return 优先级
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
