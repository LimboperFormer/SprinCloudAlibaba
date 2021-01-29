package com.lee.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author lee
 * @Date 2021/1/22
 * @Version 1.0
 */
@Component
public class ExceptionHandlerPage implements BlockExceptionHandler {
    /**
     * AuthorityException 授权异常
     * DegradeException 降级
     * FlowException 限流
     * ParamFlowException 参数限流异常
     * SystemBlockException 系统负载异常
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws Exception
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ResponseData responseData = null;
        if (e instanceof FlowException) {
            responseData = new ResponseData(200, "限流。。。。");
        } else if (e instanceof DegradeException) {
            responseData = new ResponseData(200, "降级。。。。");
        } else if (e instanceof AuthorityException) {
            responseData = new ResponseData(200, "授权。。。。");
        } else if (e instanceof ParamFlowException) {
            responseData = new ResponseData(200, "参数限流。。。。");
        } else {
            responseData = new ResponseData(200, "负载。。。。");
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(responseData));
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ResponseData {
    private int code;
    private String message;
}
