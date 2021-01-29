package com.lee.service;

import com.lee.domain.Product;
import com.lee.service.fallback.ProductServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author lee
 * @Date 2021/1/20
 * @Version 1.0
 */

@FeignClient(
        value = "service-product",
//        fallback = ProductServiceFallback.class,
        fallbackFactory = ProductServiceFallbackFactory.class)
public interface ProductService {

    /**
     * find by productId
     *
     * @param id productId
     * @return Product
     */
    @GetMapping("/product/{id}")
    Product findById(@PathVariable Integer id);

    @GetMapping("/product/{id}")
    Product findByIdSecond(@PathVariable Integer id);
}
