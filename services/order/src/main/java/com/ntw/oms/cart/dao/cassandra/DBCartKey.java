package com.ntw.oms.cart.dao.cassandra;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class DBCartKey {
    @PrimaryKeyColumn(name = "cartId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String cartId;
    @PrimaryKeyColumn(name = "cartLineId", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private int cartLineId;

    public int getCartLineId() {
        return cartLineId;
    }

    public void setCartLineId(int cartLineId) {
        this.cartLineId = cartLineId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"cartId\":" + (cartId == null ? "null" : "\"" + cartId + "\"") + ", " +
                "\"id\":\"" + cartLineId + "\"" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBCartKey cartKey = (DBCartKey) o;

        if (cartLineId != cartKey.cartLineId) return false;
        return cartId.equals(cartKey.cartId);

    }

    @Override
    public int hashCode() {
        int result = cartId.hashCode();
        result = 31 * result + cartLineId;
        return result;
    }
}
