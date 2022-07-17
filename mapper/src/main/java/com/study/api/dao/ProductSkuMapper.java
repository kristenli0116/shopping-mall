package com.study.api.dao;

import com.study.api.entity.ProductSku;
import com.study.api.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDAO<ProductSku> {
    /**
     * 根据商品ID，查询当前商品所有套餐中价格最低的套餐
     * @param productId
     * @return
     */
    List<ProductSku> selectLowestPriceByProductId(String productId);
}