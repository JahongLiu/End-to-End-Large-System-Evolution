package com.ntw.oms.order.dao;

import com.ntw.oms.order.entity.Order;

import java.util.List;

public interface OrderDao {

    public Order getOrder(String userId, String id);

    public List<Order> getOrders(String userId);

    public boolean saveOrder(Order order);

    public boolean removeOrder(String userId, String id);

    public boolean removeOrders();
}
