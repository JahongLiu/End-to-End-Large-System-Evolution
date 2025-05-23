package com.ntw.oms.order.dao.cassandra;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class DBOrderKey {
    @PrimaryKeyColumn(name = "userId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userId;
    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String id;
    @PrimaryKeyColumn(name = "orderLineId", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private int orderLineId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + (userId == null ? "null" : "\"" + userId + "\"") + ", " +
                "\"id\":" + (id == null ? "null" : "\"" + id + "\"") + ", " +
                "\"orderLineId\":\"" + orderLineId + "\"" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBOrderKey that = (DBOrderKey) o;

        if (orderLineId != that.orderLineId) return false;
        if (!userId.equals(that.userId)) return false;
        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + orderLineId;
        return result;
    }
}
