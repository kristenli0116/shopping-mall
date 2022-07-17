package com.study.api.service;

import com.study.api.entity.Orders;
import com.study.api.vo.ResultVO;

import java.sql.SQLException;
import java.util.Map;

/**
 * @auther lkx
 * @create 2022-05-08 18:04
 * @Description:
 */

public interface OrderService {
    Map<String, String> addOrder(String cids, Orders orders) throws SQLException;

    int updateOrderStatus(String orderId, String status);

    ResultVO getOrderById(String orderId);

    void closeOrder(String orderId);

    ResultVO listOrders(String userId, String status, int pageNum, int limit);
}
