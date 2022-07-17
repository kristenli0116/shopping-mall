package com.study.api.service.Impl;

import com.study.api.dao.ShoppingCartMapper;
import com.study.api.entity.ShoppingCart;
import com.study.api.entity.ShoppingCartVO;
import com.study.api.service.ShoppingCartService;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-07 11:44
 * @Description:
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int i = shoppingCartMapper.insert(cart);
        if (i>0){
            return new ResultVO(ResStatus.OK,"success",null);
        }else {
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listShoppingCartByUserId(int userId) {
        List<ShoppingCartVO> list = shoppingCartMapper.selectShoppingCartByUserId(userId);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", list);
        return resultVO;
    }

    @Override
    public ResultVO updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartNumByCartId(cartId, cartNum);
        if (i>0){
            return new ResultVO(ResStatus.OK,"update success",null);
        }else {
            return new ResultVO(ResStatus.NO,"update fail",null);
        }

    }

    @Override
    public ResultVO listShoppingCartsByCids(String cids) {
        //tkMapper只能查询某张表中存在的信息
        String[] arr = cids.split(",");
        List<Integer> cartIds = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cartIds.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> voList = shoppingCartMapper.selectShoppingCartByCids(cartIds);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "查询成功", voList);
        return resultVO;
    }


}
