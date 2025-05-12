package com.ntw.oms.order.processor;

import com.ntw.oms.order.entity.InventoryReservation;

import java.io.IOException;

public interface InventoryClient {

    public boolean reserveInventory(InventoryReservation inventoryReservationRequest) throws IOException;

}
