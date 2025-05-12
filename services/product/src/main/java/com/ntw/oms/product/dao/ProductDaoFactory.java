package com.ntw.oms.product.dao;

public interface ProductDaoFactory {
    ProductDao getProductDao(String dbType);
}
