package com.study.api.dao;

import com.study.api.entity.OrderItem;
import com.study.api.entity.Orders;
import com.study.api.general.GeneralDAO;
import io.swagger.annotations.ResponseHeader;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersMapper extends GeneralDAO<Orders> {
    List<OrderItem> selectOrders(@Param("userId") String userId,
                                 @Param("status") String status,
                                 @Param("start") int start,
                                 @Param("limit") int limit);
}