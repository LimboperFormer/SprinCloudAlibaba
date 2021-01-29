package com.lee.service.fallback;

import com.lee.domain.Product;
import com.lee.service.ProductService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lee
 * @version 1.0
 * @date 2021/1/27
 */

//容错类。要求实现一个FallbackFactory<?>  ?表示哪个类
@Slf4j
@Component
public class ProductServiceFallbackFactory implements FallbackFactory<ProductService> {
    @Override
    public ProductService create(Throwable cause) {
        return new ProductService() {
            @Override
            public Product findById(Integer id) {

                log.error("error:{}", cause);
                Product product = new Product();
                product.setId(-100);
                product.setName("远程调用出现异常，进入容错逻辑");
                return product;
            }

            @Override
            public Product findByIdSecond(Integer id) {
                return null;
            }
        };
    }
}
