package com.study.api.controller;

import com.study.api.dao.ProductMapper;
import com.study.api.service.CategoryService;
import com.study.api.service.IndexImgService;
import com.study.api.service.ProductService;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther lkx
 * @create 2022-05-02 13:23
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/index")
@Api(value = "提供首页数据显示所需的接口",tags = "首页管理")
public class IndexController {

    @Autowired
    private IndexImgService indexImgService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/indexImg")
    @ApiOperation("首页轮播图接口")
    public ResultVO listIndexImgs(){
        return indexImgService.listIndexImg();
    }


    @GetMapping("/category")
    @ApiOperation("商品查询接口")
    public ResultVO listCategory(){
        return categoryService.listCategories();
    }


    @GetMapping("/list-recommend")
    @ApiOperation("新品推荐接口")
    public ResultVO listRecommendProducts(){
        return productService.listRecommendProducts();
    }

    @GetMapping("/category-recommends")
    @ApiOperation("分类推荐接口")
    public ResultVO listRecommendProductsByCategory(){
        return  categoryService.listFirstLevelCategories();
    }
}
