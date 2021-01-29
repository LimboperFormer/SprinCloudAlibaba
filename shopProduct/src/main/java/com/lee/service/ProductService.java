package com.lee.service;

import com.lee.domain.Product;

/**
 * @Author lee
 * @Date 2021/1/19
 * @Version 1.0
 */
public interface ProductService {
    /**
     * find by id
     * @param pid productId
     * @return Product
     */
    Product findById(Integer pid);
}
