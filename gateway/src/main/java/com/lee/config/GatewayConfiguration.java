package com.lee.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author lee
 * @version 1.0
 * @date 2021/2/2
 */
@Configuration
public class GatewayConfiguration {

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 初始化一个限流的过滤器
     *
     * @return 全局过滤器 GlobalFilter
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 配置初始化的限流参数 (针对路由维度进行限流)
     */
    @PostConstruct
    public void intiGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        //product_route   资源名称，对应路由id   count=1 限流阈值   intervalSec=1 统计时间窗口 单位是秒，默认是1
//        rules.add(
//                new GatewayFlowRule("product_route")
//                        .setCount(100)
//                        .setIntervalSec(100)
//        );
        rules.add(new GatewayFlowRule("product_api1").setCount(1).setIntervalSec(1));
        rules.add(new GatewayFlowRule("product_api2").setCount(1).setIntervalSec(1));
        GatewayRuleManager.loadRules(rules);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @PostConstruct
    public void initBlockHandler() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            Map map = new HashMap<>(16);
            map.put("code", 0);
            map.put("message", "接口被限流了");
            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(map));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    /**
     * 自定义api分组
     */
    @PostConstruct
    private void initCustomizedApis() {
        HashSet<ApiDefinition> apiDefinitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("product_api1")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem()
                            .setPattern("/product-serv/product/api1/**")
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});

        ApiDefinition api2 = new ApiDefinition("product_api2")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem()
                            .setPattern("/product-serv/product/api2/demo1"));
                }});
        apiDefinitions.add(api1);
        apiDefinitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(apiDefinitions);
    }

}
