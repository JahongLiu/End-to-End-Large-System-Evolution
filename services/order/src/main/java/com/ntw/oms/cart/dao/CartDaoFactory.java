package com.ntw.oms.cart.dao;

public interface CartDaoFactory {
    CartDao getCartDao(String dbType);
}
