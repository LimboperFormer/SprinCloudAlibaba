package com.lee.service.fallback;

import com.lee.domain.Product;
import com.lee.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author lee
 * @version 1.0
 * @date 2021/1/27
 */

//容错类，需要实现feign所在的接口，并且需要实现接口中的所有方法
// 当feign远程调用出现问题，就回进入当前类。调用同名的方法 ，执行容错逻辑
@Service
public class ProductServiceFallback implements ProductService {
    @Override
    public Product findById(Integer id) {
        //容错逻辑
        Product product = new Product();
        product.setId(-100);
        product.setName("远程调用出现异常，进入容错逻辑");
        return product;
    }

    @Override
    public Product findByIdSecond(Integer id) {
        //容错逻辑
        Product product = new Product();
        product.setId(-100);
        product.setName("远程调用出现异常，进入容错逻辑");
        return product;
    }
}
