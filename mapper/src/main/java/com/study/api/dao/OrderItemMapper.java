package com.study.api.dao;

import com.study.api.entity.OrderItem;
import com.study.api.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper extends GeneralDAO<OrderItem> {

    List<OrderItem> listOrderItemsByOrderId(String orderId);
}