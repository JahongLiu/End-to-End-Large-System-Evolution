package com.ntw.oms.admin.api;

import com.ntw.common.config.AppConfig;
import com.ntw.common.config.ServiceID;
import com.ntw.oms.admin.entity.Inventory;

public class InventoryApiClient extends ApiClient {

    @Override
    protected ServiceID getServiceID() {
        return ServiceID.InventorySvc;
    }

    @Override
    protected String getServiceURI() {
        return AppConfig.INVENTORY_RESOURCE_PATH;
    }

    @Override
    protected Object createObject(String id) {
        return createInventory(id);
    }

    @Override
    protected String getObjectId(int index) {
        return getProductId(index);
    }

    private Inventory createInventory(String productId) {
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setQuantity(10000);
        return inventory;
    }

}
