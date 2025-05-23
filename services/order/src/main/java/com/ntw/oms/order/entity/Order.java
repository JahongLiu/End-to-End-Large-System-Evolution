package com.ntw.oms.order.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private String id;
    private String userId;
    private OrderStatus status;
    private Date createdDate;
    private List<OrderLine> orderLines;

    public Order() {
        this.orderLines = new LinkedList<>();
    }

    public Order(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.orderLines = new LinkedList<>();
    }

    public Order(Order orderEntity) {
        this.id = orderEntity.id;
        this.userId = orderEntity.userId;
        this.status = orderEntity.status;
        this.createdDate = orderEntity.createdDate;
        this.orderLines = orderEntity.orderLines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = OrderStatus.getStatus(status);
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + (id == null ? "null" : "\"" + id + "\"") + ", " +
                "\"userId\":" + (userId == null ? "null" : "\"" + userId + "\"") + ", " +
                "\"status\":" + (status == null ? "null" : status) + ", " +
                "\"createdDate\":" + (createdDate == null ? "null" : createdDate) + ", " +
                "\"orderLines\":" + (orderLines == null ? "null" : Arrays.toString(orderLines.toArray())) +
                "}";
    }
}
