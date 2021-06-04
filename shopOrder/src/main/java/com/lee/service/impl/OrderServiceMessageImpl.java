package com.lee.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lee.exception.ThrowableException;
import com.lee.handler.BlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author lee
 * @Date 2021/1/21
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceMessageImpl {

    int i = 0;

    /**
     * value指定资源名称
     * sentinel注解的作用是定义一个资源
     * 定义当资源内部发生异常的时候
     * blockHandler：当资源内部发生了BlockException（捕获的是Sentinel定义的异常）时应该进入的方法
     * fallback：定义当资源内部发生了Throwable应进入的异常
     */
    @SentinelResource(
            value = "message",
            blockHandler = "blockHandlerMethod",
            blockHandlerClass = BlockHandler.class,
            fallback = "fallbackMethod",
            fallbackClass = ThrowableException.class
    )
    public String message(String name) {
        i++;
        if (i % 3 == 0) {
            throw new RuntimeException("runTimeException");
        }
        return "message";
    }

}
