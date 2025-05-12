package com.ntw.oms.cart.service;

import com.ntw.oms.order.entity.InventoryReservation;
import com.ntw.oms.order.processor.InventoryClient;

import java.io.IOException;

public class InventoryMockClient implements InventoryClient {
    @Override
    public boolean reserveInventory(InventoryReservation invRes) throws IOException {
        return true;
    }
}
