package com.lee.controller;

import com.alibaba.fastjson.JSON;
import com.lee.domain.Product;
import com.lee.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * query product by Id
     *
     * @param pid productId
     * @return Product
     */
    @RequestMapping("/product/{pid}")
    public Product product(@PathVariable("pid") Integer pid) {
        log.info("starting get {} product", pid);
        Product product = productService.findById(pid);
        log.info("query success,Product:{}", JSON.toJSONString(product));
        return product;
    }
}
