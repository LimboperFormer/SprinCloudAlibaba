package com.lee.config.predicates;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author lee
 * @version 1.0
 * @date 2021/2/1
 */
@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {


    public AgeRoutePredicateFactory() {
        super(AgeRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            //1：接收前台传入的age参数
            String ageStr = serverWebExchange.getRequest().getQueryParams().getFirst("age");

            //2：先判断是否为空
            if (StringUtils.isNotEmpty(ageStr)) {
                //3：如果不为空，再进行路由逻辑判断
                int age = Integer.parseInt(ageStr);
                return age > config.getMinAge() && age < config.getMaxAge();
            }
            return false;
        };
    }

    /**
     * 读取配置文件中的属性值  赋值到Config的属性中
     *
     * @return List<String>
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("minAge", "maxAge");
    }

    public static class Config {
        private int minAge;
        private int maxAge;

        public Config() {
        }

        public Config(int minAge, int maxAge) {
            this.minAge = minAge;
            this.maxAge = maxAge;
        }

        public int getMinAge() {
            return minAge;
        }

        public void setMinAge(int minAge) {
            this.minAge = minAge;
        }

        public int getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(int maxAge) {
            this.maxAge = maxAge;
        }
    }
}
