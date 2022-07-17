package com.study.api.dao;

import com.study.api.entity.Product;
import com.study.api.entity.ProductVO;
import com.study.api.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductMapper extends GeneralDAO<Product> {
    List<Product> selectRecommendProducts();

    /**
     * 查询一级类别下销量最高的6个商品
     * @param cid
     * @return
     */
    List<ProductVO> selectTop6ByCategory(int cid);

    /**
     * 根据三级分类ID分页查询商品信息
     * @param cid
     * @param start
     * @param limit
     * @return
     */
    List<ProductVO> selectProductByCategoryId(@Param("cid") int cid,
                                              @Param("start") int start,
                                              @Param("limit") int limit);

    List<String> selectBrandByCategoryId(int cid);

    /**
     * 根据关键子模糊搜索商品信息
     *
     * @param keyword
     * @param start
     * @param limit
     * @return
     */
    List<ProductVO> selectProductByKeyword(@Param("kw") String keyword,
                                                   @Param("start") int start,
                                                   @Param("limit") int limit);

    /**
     * 根据关键字查询相关商品的品牌
     * @param kw
     * @return
     */
    List<String> selectBrandByKeyword(String kw);

}