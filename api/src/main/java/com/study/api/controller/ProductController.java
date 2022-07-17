package com.study.api.controller;

import com.study.api.service.ProductCommentsService;
import com.study.api.service.ProductService;
import com.study.api.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ApiListing;

import javax.websocket.server.PathParam;

/**
 * @auther lkx
 * @create 2022-05-05 23:21
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("product")
@Api(value = "提供商品信息相关的接口",tags = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCommentsService productCommentsService;

    @GetMapping("/detail-info/{pid}")
    @ApiOperation("商品基本信息查询接口")
    public ResultVO getProductBasicInfo(@PathVariable("pid") String pid){
        return productService.getProductBasicInfo(pid);
    }

    @GetMapping("/detail-params/{pid}")
    @ApiOperation("商品参数信息接口")
    public ResultVO getProductParamsById(@PathVariable("pid") String pid){
        return productService.getProductParamsById(pid);
    }


    @GetMapping("/detail-comment/{pid}")
    @ApiOperation("商品评论信息查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    public ResultVO listCommentsByProductId(@PathVariable("pid") String pid,int pageNum,int limit){
        return productCommentsService.listCommentsByProductId(pid,pageNum,limit);
    }

    @ApiOperation("商品评价统计查询接口")
    @GetMapping("/detail-commentCount/{pid}")
    public ResultVO getCommentsCountByProductId(@PathVariable("pid") String pid){
        return productCommentsService.getCommentsCountByProductId(pid);
    }

    @ApiOperation("根据类别查询商品接口")
    @GetMapping("/listByCid/{cid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    public ResultVO getProductByCategoryId(@PathVariable("cid") int cid,int pageNum,int limit){
        return productService.getProductByCategoryId(cid, pageNum, limit);
    }

    @ApiOperation("根据类别查询商品品牌接口")
    @GetMapping("/listBrands/{cid}")
    public ResultVO getBrandsByCategoryId(@PathVariable("cid") int cid){
        return productService.listBrands(cid);
    }

    @ApiOperation("根据关键字查询商品接口")
    @GetMapping("/listByKeyword")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "keyword", value = "搜索关键字",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "每页显示条数",required = true)
    })
    public ResultVO searchProducts(String keyword,int pageNum,int limit){
        return productService.searchProduct(keyword, pageNum, limit);
    }

    @ApiOperation("根据关键字查询商品品牌接口")
    @ApiImplicitParam(dataType = "string",name = "keyword",value = "搜索关键字",required = true)
    @GetMapping("/listBrandsByKeyword")
    public ResultVO getBrandsByKeyword(String keyword){
        return productService.listBrands(keyword);
    }


}
