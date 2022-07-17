package com.study.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-05 15:35
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVO {
    private List<ProductImg> imgs;//查询商品的时候，关联查询图片信息

    private List<ProductSku> skus;//查询商品的时候，关联查询套餐信息
    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "root_category_id")
    private Integer rootCategoryId;

    @Column(name = "sold_num")
    private Integer soldNum;

    @Column(name = "product_status")
    private Integer productStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String content;

}
