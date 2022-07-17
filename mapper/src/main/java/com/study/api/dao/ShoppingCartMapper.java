package com.study.api.dao;

import com.study.api.entity.ShoppingCart;
import com.study.api.entity.ShoppingCartVO;
import com.study.api.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDAO<ShoppingCart> {
    List<ShoppingCartVO> selectShoppingCartByUserId(int userId);

    int updateCartNumByCartId(@Param("cartId") int cartId,
                              @Param("cartNum") int cartNum);

    List<ShoppingCartVO> selectShoppingCartByCids(List<Integer> cids);
}