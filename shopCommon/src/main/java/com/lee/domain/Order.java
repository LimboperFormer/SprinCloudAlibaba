package com.lee.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author lee
 * @Date 2021/1/19
 * @Version 1.0
 */
@Data
@Entity(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    private Integer userId;
    private String username;

    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品单价
     */
    private Double productPrice;

    /**
     * 购买数量
     */
    private Integer number;

}
