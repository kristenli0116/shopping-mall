package com.study.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @auther lkx
 * @create 2022-05-07 18:07
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartVO {

    @Id
    @Column(name = "cart_id")
    private Integer cartId;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "sku_id")
    private String skuId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "cart_num")
    private String cartNum;
    @Column(name = "cart_time")
    private String cartTime;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    @Column(name = "sku_props")
    private String skuProps;

    private String productName;
    private String productImg;
    private Double originalPrice;
    private Double sellPrice;
    private String skuName;
    private int skuStock;
}
