package com.ntw.oms.inventory.dao.cql;

import com.ntw.oms.inventory.entity.Inventory;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("Inventory")
public class DBInventory {
    @PrimaryKey
    private String productId;
    private float quantity;

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
                "\"productId\":" + (productId == null ? "null" : "\"" + productId + "\"") + ", " +
                "\"quantity\":\"" + quantity + "\"" +
                "}";
    }

    public static DBInventory createInventory(Inventory inventory) {
        DBInventory dbInventory = new DBInventory();
        dbInventory.setProductId(inventory.getProductId());
        dbInventory.setQuantity(inventory.getQuantity());
        return dbInventory;
    }

    public Inventory getInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(getProductId());
        inventory.setQuantity(getQuantity());
        return inventory;
    }
    
}
