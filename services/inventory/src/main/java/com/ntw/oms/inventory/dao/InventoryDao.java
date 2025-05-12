package com.ntw.oms.inventory.dao;

import com.ntw.oms.inventory.entity.Inventory;
import com.ntw.oms.inventory.entity.InventoryReservation;

import java.util.List;

public interface InventoryDao {

    public List<Inventory> getInventory();

    public Inventory getInventory(String productId);

    public boolean updateInventory(Inventory inventory);

    public boolean reserveInventory(InventoryReservation inventoryReservation);

    public boolean insertInventory(Inventory inventory);

    public boolean deleteInventory();
}
