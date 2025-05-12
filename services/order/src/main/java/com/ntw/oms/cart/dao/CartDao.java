package com.ntw.oms.cart.dao;

import com.ntw.oms.cart.entity.Cart;

public interface CartDao {

    public Cart getCart(String id);

    public boolean saveCart(Cart cart);

    public boolean updateCart(Cart cart);

    public boolean removeCart(String id);

    public boolean removeCarts();
}
