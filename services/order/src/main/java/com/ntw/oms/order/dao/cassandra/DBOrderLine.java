package com.ntw.oms.order.dao.cassandra;

import com.ntw.oms.order.entity.Order;
import com.ntw.oms.order.entity.OrderLine;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Table("OrderLine")
public class DBOrderLine {
    @PrimaryKey
    private DBOrderKey orderKey;

    private String productId;
    private float quantity;
    private String status;
    private LocalDate createdDate;
    private LocalTime createdTime;

    public DBOrderKey getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(DBOrderKey orderKey) {
        this.orderKey = orderKey;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"orderKey\":" + (orderKey == null ? "null" : orderKey) + ", " +
                "\"productId\":" + (productId == null ? "null" : "\"" + productId + "\"") + ", " +
                "\"quantity\":\"" + quantity + "\"" + ", " +
                "\"status\":" + (status == null ? "null" : "\"" + status + "\"") + ", " +
                "\"createdDate\":" + (createdDate == null ? "null" : createdDate) + ", " +
                "\"createdTime\":" + (createdTime == null ? "null" : createdTime) +
                "}";
    }

    public static List<DBOrderLine> createDBOrder(Order order) {
        List<DBOrderLine> dbOrderLines = new LinkedList<>();
        LocalDate localDate = new java.sql.Date(order.getCreatedDate().getTime()).toLocalDate();
        LocalTime localTime = new java.sql.Time(order.getCreatedDate().getTime()).toLocalTime();
        for (OrderLine orderLine : order.getOrderLines()) {
            DBOrderKey orderKey = new DBOrderKey();
            orderKey.setUserId(order.getUserId());
            orderKey.setId(order.getId());
            orderKey.setOrderLineId(orderLine.getId());
            DBOrderLine dbOrderLine = new DBOrderLine();
            dbOrderLine.setOrderKey(orderKey);
            dbOrderLine.setProductId(orderLine.getProductId());
            dbOrderLine.setQuantity(orderLine.getQuantity());
            dbOrderLine.setStatus(order.getStatus().toString());
            if (order.getCreatedDate() != null) {
                dbOrderLine.setCreatedDate(localDate);
                dbOrderLine.setCreatedTime(localTime);
            }
            dbOrderLines.add(dbOrderLine);
        }
        return dbOrderLines;
    }

    public static Order getOrder(String userId, String id, List<DBOrderLine> dbOrderLines) {
        Order order = new Order();
        order.setId(id);
        order.setUserId(userId);
        if (dbOrderLines.size() > 0) {
            order.setId(dbOrderLines.get(0).getOrderKey().getId());
            order.setUserId(dbOrderLines.get(0).getOrderKey().getUserId());
            order.setStatus(dbOrderLines.get(0).getStatus());
            LocalDateTime localDateTime = LocalDateTime.of(dbOrderLines.get(0).getCreatedDate(),
                    dbOrderLines.get(0).getCreatedTime());
            Date createdDate = new Date(java.sql.Timestamp.valueOf(localDateTime).getTime());
            order.setCreatedDate(createdDate);
        }
        for (DBOrderLine dbOrderLine : dbOrderLines) {
            OrderLine orderLine = new OrderLine();
            orderLine.setId(dbOrderLine.getOrderKey().getOrderLineId());
            orderLine.setProductId(dbOrderLine.getProductId());
            orderLine.setQuantity(dbOrderLine.getQuantity());
            order.getOrderLines().add(orderLine);
        }
        return order;
    }
}
