package com.lee.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @Author lee
 * @Date 2021/1/25
 * @Version 1.0
 */
public class BlockHandler extends BlockException {

    public BlockHandler(String ruleLimitApp) {
        super(ruleLimitApp);
    }

    public static String blockHandlerMethod(String name,BlockException e) {
        return "blockHandlerMethod";

    }
}
