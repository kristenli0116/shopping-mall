package com.study.api;

import com.study.api.dao.*;
import com.study.api.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-04 9:48
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ApiTest {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCommentsMapper productCommentsMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    public void testRecommend(){
        List<Product> productList = productMapper.selectRecommendProducts();
        for (Product p :
                productList) {
            System.out.println(p);
        }
    }

    @Test
    public  void  testSelectFirstLevelCategory(){
        List<CategoryVO> categoryList = categoryMapper.selectFirstLevelCategories();
        for (CategoryVO c :
                categoryList) {
            System.out.println(c);
        }
    }

//    @Test
//    public void testGetProductParamsById(){
//        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.seletCommontsByProductId("3");
//        for (ProductCommentsVO p : productCommentsVOS) {
//            System.out.println(p);
//        }
//    }


    @Test
    public void testShoppingCart(){
        List<ShoppingCartVO> cartVOList = shoppingCartMapper.selectShoppingCartByUserId(6);
        System.out.println(cartVOList);
    }

//    @Test
//    public void testShopCart(){
//       List<Integer> cids = new ArrayList<>();
//        cids.add(9);
//        cids.add(10);
//        cids.add(11);
//
//        List<ShoppingCartVO> shoppingCartVOList = shoppingCartMapper.selectShoppingCartByCids(cids);
//        System.out.println(shoppingCartVOList);
//    }

    @Test
    public void testShopCart(){
        String cids = "9,10";
        String[] arr = cids.split(",");
        List<Integer> cidsList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cidsList.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> voList = shoppingCartMapper.selectShoppingCartByCids(cidsList);
        for (ShoppingCartVO sc: voList) {
            System.out.println(sc);
        }
    }


    @Autowired
    private OrdersMapper ordersMapper;
    @Test
    public void testTimeOutCheck(){
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");

        Date time = new Date(System.currentTimeMillis() - 30*60*1000);
        criteria.andEqualTo("createTime",time);

        List<Orders> orders = ordersMapper.selectByExample(example);
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(orders.get(i).getOrderId()+"\t" +orders.get(i).getCreateTime() + "\t"
            + orders.get(i).getStatus());
        }
    }
}
