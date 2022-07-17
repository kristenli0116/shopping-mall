package com.study.api.service.Impl;

import com.study.api.dao.OrderItemMapper;
import com.study.api.dao.OrdersMapper;
import com.study.api.dao.ProductSkuMapper;
import com.study.api.dao.ShoppingCartMapper;
import com.study.api.entity.OrderItem;
import com.study.api.entity.Orders;
import com.study.api.entity.ProductSku;
import com.study.api.entity.ShoppingCartVO;
import com.study.api.service.OrderService;
import com.study.api.utils.PageHelper;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

/**
 * @auther lkx
 * @create 2022-05-08 18:05
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;

    /**
     * 保存订单业务
     * @param cids
     * @param orders
     * @return
     */
    @Transactional
    public Map<String,String> addOrder(String cids, Orders orders) {
        Map<String, String> Map = new HashMap<>();

        //1.校验库存------根据cids查询当前订单中关联的购物车记录详情（包括库存）
        String[] arr = cids.split(",");
        List<Integer> cidsList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cidsList.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> voList = shoppingCartMapper.selectShoppingCartByCids(cidsList);

        boolean f =true;
        String untitled = "";
        for (ShoppingCartVO v : voList) {
            if (Integer.parseInt(v.getCartNum()) > v.getSkuStock()){
                f=false;
            }
            untitled = untitled + v.getProductName() + ",";
        }

        if (f){
            //2.保存订单------表示库存充足
            //a. userId   b. untitled  c. 收货人信息：姓名、电话、地址
            // d. 总价格 e. 支付方式  f.订单船舰时间
            orders.setUntitled(untitled);
            orders.setCreateTime(new Date());
            orders.setStatus("1");
            //生成订单编号
            String orderId = UUID.randomUUID().toString().replace("-", "");
            orders.setOrderId(orderId);
            ordersMapper.insert(orders);


            //3.生成商品快照
            for (ShoppingCartVO sc: voList) {
                int cnum = Integer.parseInt(sc.getCartNum());
                String itemId = System.currentTimeMillis()+""+(new Random().nextInt(89999)+10000);

                OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(), sc.getProductName(),
                        sc.getProductImg(), sc.getSkuId(), sc.getSkuName(), new BigDecimal(sc.getSellPrice()) ,cnum,
                        new BigDecimal(sc.getSellPrice() * cnum),  new Date(), new Date(), 0);
                orderItemMapper.insert(orderItem);
            }


            //4.扣减库存:根据套餐ID秀爱套餐库存量
            for (ShoppingCartVO sc: voList) {
                String skuId = sc.getSkuId();
                int newStock = sc.getSkuStock() - Integer.parseInt(sc.getCartNum());

                ProductSku productSku = new ProductSku();
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }
            //5.删除购物车：当购物车中的记录购买成功之后，购物车中对应做删除操作
            for (int cid: cidsList) {
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }

            Map.put("orderId",orderId);
            Map.put("productNames",untitled);
            return Map;
        }else {
            //表示库存不足
            return null;
        }
    }

    @Override
    public int updateOrderStatus(String orderId, String status) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);
        int result = ordersMapper.updateByPrimaryKeySelective(orders);
        return result;
    }

    @Override
    public ResultVO getOrderById(String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        return new ResultVO(ResStatus.OK,"success",orders.getStatus());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeOrder(String orderId) {
        synchronized (this) {
            //1.修改当前订单：status=6 已关闭  close_type=1 超时未支付
            Orders cancleOrder = new Orders();
            cancleOrder.setOrderId(orderId);
            cancleOrder.setStatus("6");        //已关闭
            cancleOrder.setCloseType(1);        //超时未支付
            ordersMapper.updateByPrimaryKeySelective(cancleOrder);

            //2.还原库存，现根据当前订单编号查询商品快照表
            Example example1 = new Example(OrderItem.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("orderId", orderId);
            List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);

            //还原库存
            for (int j = 0; j < orderItems.size(); j++) {
                OrderItem orderItem = orderItems.get(j);
                //修改
                ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
                productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());
                productSkuMapper.updateByPrimaryKey(productSku);
            }
        }
    }

    @Override
    public ResultVO listOrders(String userId, String status, int pageNum, int limit) {

        //1.分页查询
        int start=(pageNum-1)*limit;
        List<OrderItem> orderItems = ordersMapper.selectOrders(userId, status, start, limit);

        //2.查询总记录数
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userId",userId);
        if (status != null&& !"".equals(status)){
            criteria.andLike("status",status);
        }
        int count = ordersMapper.selectCountByExample(example);
        //3.计算总页数
        int pageCount = count%limit==0? count/limit:count/limit+1;

        //4.封装数据
        PageHelper<OrderItem> pageHelper = new PageHelper<>(count, pageCount, orderItems);

        return new ResultVO(ResStatus.OK,"success",pageHelper);
    }
}
