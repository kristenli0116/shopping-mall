package com.study.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-05 15:32
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryVO {
    private Integer categoryId;
    private String categoryName;
    private Integer categoryLevel;
    private Integer parentId;
    private String categoryIcon;
    private String categorySlogan;
    private String categoryPic;
    private String categoryBgColor;
    //实现首页分类商品推荐
    private List<ProductVO> products;
    //实现首页的类别显示
    private List<CategoryVO> categories;
}
