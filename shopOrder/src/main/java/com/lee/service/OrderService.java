package com.lee.service;

import com.lee.domain.Order;
import org.springframework.stereotype.Service;

/**
 * @Author lee
 * @Date 2021/1/19
 * @Version 1.0
 */

@Service
public interface OrderService {
    /**
     * create Order
     * @param order order
     */
    void createOrder(Order order);

}
