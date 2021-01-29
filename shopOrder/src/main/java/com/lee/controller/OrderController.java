package com.lee.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.lee.domain.Order;
import com.lee.domain.Product;
import com.lee.service.OrderService;
import com.lee.service.ProductService;
import com.lee.service.impl.OrderServiceMessageImpl;
import io.netty.handler.codec.ProtocolDetectionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lee
 * @Date 2021/1/19
 * @Version 1.0
 */
@RestController
@Slf4j
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderServiceMessageImpl orderServiceMessage;

    @RequestMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {
        log.info("receive {},and then call product-server to query product message ", pid);

        Product product = productService.findById(pid);

        if (product.getId() == -100) {
            Order order = new Order();
            order.setId(-100L);
            order.setProductName("下单失败");
            return order;
        }


        log.info("query success,pid:{},product:{}", pid, JSON.toJSONString(product));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.trace(e.getMessage());
        }
        Order order = new Order();
        order.setUserId(1);
        order.setUsername("test user");

        order.setProductId(pid);
        assert product != null;
        order.setProductName(product.getName());
        order.setProductPrice(product.getPrice());
        order.setNumber(1);

//        orderService.createOrder(order);

        log.info("create Order Success,orderMessage:{}", JSON.toJSONString(order));

        return order;
    }


    @RequestMapping("/order/m1")
    public String orderMessage1() {
        return "orderMessage1";
    }


    int i = 0;

    @RequestMapping("/order/m2")
    public String orderMessage2() {
        i++;
        if (i % 3 == 0) {
            throw new RuntimeException("runTimeException");
        }
        return "orderMessage2";
    }


    @RequestMapping("/order/m3")
    @SentinelResource("message3")
    public String orderMessage3(String name, String age) {
        return "message3" + name + "\tage" + age;
    }

    @RequestMapping("order/mes")
    public String message() {
        return orderServiceMessage.message("xx");
    }


}
