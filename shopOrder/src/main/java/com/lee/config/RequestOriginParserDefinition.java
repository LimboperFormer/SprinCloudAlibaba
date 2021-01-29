package com.lee.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lee
 * @Date 2021/1/22
 * @Version 1.0
 */
//@Component
public class RequestOriginParserDefinition implements RequestOriginParser {
    /**
     * 定义区分来源： 本质是用过request域获取到来源标识
     * 交给流控应用进行匹配
     *
     * @param req req
     * @return serviceName
     */
    @Override
    public String parseOrigin(HttpServletRequest req) {
        String serviceName = req.getParameter("serviceName");
        if (StringUtils.isEmpty(serviceName)) {
            throw new RuntimeException("serviceName is not Empty");
        }
        return serviceName;
    }
}
