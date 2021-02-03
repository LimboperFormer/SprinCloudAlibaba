package com.lee.config.filters.gatewayFilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * gateway过滤器
 *
 * @author lee
 * @version 1.0
 * @date 2021/2/2
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    public LogGatewayFilterFactory() {
        super(LogGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isCacheLog()) {
                System.out.println("this is cacheLog,cacheLog starting ");
            }
            if (config.isConsoleLog()) {
                System.out.println("this is cacheLog,cacheLog starting ");
            }
            return chain.filter(exchange);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog", "cacheLog");
    }

    public static class Config {
        private boolean consoleLog;
        private boolean cacheLog;

        public Config() {
        }

        public Config(boolean consoleLog, boolean cacheLog) {
            this.consoleLog = consoleLog;
            this.cacheLog = cacheLog;
        }

        public boolean isConsoleLog() {
            return consoleLog;
        }

        public void setConsoleLog(boolean consoleLog) {
            this.consoleLog = consoleLog;
        }

        public boolean isCacheLog() {
            return cacheLog;
        }

        public void setCacheLog(boolean cacheLog) {
            this.cacheLog = cacheLog;
        }
    }

}
