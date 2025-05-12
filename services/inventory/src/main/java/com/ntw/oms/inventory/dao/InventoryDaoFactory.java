package com.ntw.oms.inventory.dao;

public interface InventoryDaoFactory {
    InventoryDao getInventoryDao(String dbType);
}
