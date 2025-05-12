package com.ntw.oms.admin.api;

import com.ntw.common.config.AppConfig;
import com.ntw.common.config.ServiceID;
import com.ntw.oms.admin.entity.Product;

public class ProductApiClient extends ApiClient {

    @Override
    protected ServiceID getServiceID() {
        return ServiceID.ProductSvc;
    }

    @Override
    protected String getServiceURI() {
        return AppConfig.PRODUCTS_RESOURCE_PATH;
    }

    @Override
    protected Object createObject(String id) {
        return createSampleProduct(id);
    }

    @Override
    protected String getObjectId(int index) {
        return getProductId(index);
    }

    private Product createSampleProduct(String id) {
        Product product = new Product();
        product.setId(id);
        product.setName(getNameFromId(id));
        product.setPrice(10F);
        return product;
    }

}
