package com.ntw.oms.cart.dao.cassandra;

import com.ntw.oms.cart.entity.Cart;
import com.ntw.oms.cart.entity.CartLine;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.LinkedList;
import java.util.List;

@Table("CartLine")
public class DBCartLine {
    @PrimaryKey
    private DBCartKey cartKey;

    private String productId;
    private float quantity;

    public DBCartKey getCartKey() {
        return cartKey;
    }

    public void setCartKey(DBCartKey cartKey) {
        this.cartKey = cartKey;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "{" +
                "\"cartKey\":" + (cartKey == null ? "null" : cartKey.toString()) + ", " +
                "\"productId\":" + (productId == null ? "null" : "\"" + productId + "\"") + ", " +
                "\"quantity\":\"" + quantity + "\"" +
                "}";
    }

    public static List<DBCartLine> createDBCart(Cart cart) {
        List<DBCartLine> dbCartLines = new LinkedList<>();
        for (CartLine cartLine : cart.getCartLines()) {
            DBCartKey cartKey = new DBCartKey();
            cartKey.setCartLineId(cartLine.getId());
            cartKey.setCartId(cart.getId());
            DBCartLine dbCartLine = new DBCartLine();
            dbCartLine.setCartKey(cartKey);
            dbCartLine.setProductId(cartLine.getProductId());
            dbCartLine.setQuantity(cartLine.getQuantity());
            dbCartLines.add(dbCartLine);
        }
        return dbCartLines;
    }

    public static Cart getCart(String cartId, List<DBCartLine> dbCartLines) {
        Cart cart = new Cart();
        cart.setId(cartId);
        for (DBCartLine dbCartLine : dbCartLines) {
            CartLine cartLine = new CartLine();
            cartLine.setId(dbCartLine.getCartKey().getCartLineId());
            cartLine.setProductId(dbCartLine.getProductId());
            cartLine.setQuantity(dbCartLine.getQuantity());
            cart.getCartLines().add(cartLine);
        }
        return cart;
    }
}
