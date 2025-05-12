package com.ntw.oms.order.dao;

public interface OrderDaoFactory {
    OrderDao getOrderDao(String dbType);
}
