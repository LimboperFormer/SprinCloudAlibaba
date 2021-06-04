package com.lee.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lee
 * @version 1.0
 * @date 2021/5/7
 */

@RestController
@RefreshScope
public class TestController {
    @Value("${config.address:asd}")
    @NacosValue(value = "")
    private String configAddress;

    @Value("${server.port}")
    private String serverPort;


    @RequestMapping("he")
    public String printConfig(){
        return configAddress+"\t\t"+serverPort;
    }
}
