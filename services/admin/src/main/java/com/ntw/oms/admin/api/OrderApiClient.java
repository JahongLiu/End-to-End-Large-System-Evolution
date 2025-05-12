package com.ntw.oms.admin.api;

import com.ntw.common.config.AppConfig;
import com.ntw.common.config.ServiceID;


public class OrderApiClient extends ApiClient {

    @Override
    protected ServiceID getServiceID() {
        return ServiceID.OrderSvc;
    }

    @Override
    protected String getServiceURI() {
        return AppConfig.ORDERS_RESOURCE_PATH;
    }

    @Override
    protected Object createObject(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getObjectId(int index) {
        throw new UnsupportedOperationException();
    }

}
