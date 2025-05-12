package com.ntw.oms.cart.dao;

import com.ntw.oms.cart.config.TestConfig;
import com.ntw.oms.cart.entity.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartMockDao implements CartDao {

    private static final Logger logger = LoggerFactory.getLogger(CartMockDao.class);
    @Override
    public Cart getCart(String id) {
        if (id.equals(TestConfig.TEST_USER_ID)) {
            return TestConfig.createCart(TestConfig.TEST_USER_ID);
        }
        return null;
    }

    @Override
    public boolean saveCart(Cart cart) {
        return true;
    }

    @Override
    public boolean updateCart(Cart cart) {
        return true;
    }

    @Override
    public boolean removeCart(String userId) {
        return true;
    }

    @Override
    public boolean removeCarts() {
        return false;
    }

}
