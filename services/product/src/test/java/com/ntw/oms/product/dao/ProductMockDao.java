package com.ntw.oms.product.dao;

import com.ntw.oms.product.config.TestConfig;
import com.ntw.oms.product.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ProductMockDao implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductMockDao.class);
    @Override
    public List<Product> getProducts() {
        List<Product> products = new LinkedList<Product>();
        products.add(getProduct(TestConfig.productTestId_1));
        return products;
    }

    @Override
    public Product getProduct(String id) {
        if (id.equals(TestConfig.productTestId_Bad)) {
            logger.debug("Unable to find product; context={}", id);
            return null;
        }
        Product product = TestConfig.createProduct(id);
        logger.debug("Found product; context={}", product);
        return product;
    }

    @Override
    public List<Product> getProducts(List<String> ids) {
        return null;
    }

    @Override
    public boolean addProduct(Product product) {
        if (product.getId().equals(TestConfig.productTestId_Bad)) {
            logger.debug("Unable to add product; context={}", product.getId());
            return false;
        }
        logger.debug("Added product; context={}", product);
        return true;
    }

    @Override
    public Product modifyProduct(Product product) {
        logger.debug("Modified product; context={}", product);
        return product;
    }

    @Override
    public boolean removeProduct(String id) {
        if (id.equals(TestConfig.productTestId_Bad)) {
            logger.debug("Unable to remove product; context={}", id);
            return false;
        }
        Product product = getProduct(id);
        logger.debug("Removed product; context={}", product);
        return true;
    }

    @Override
    public boolean removeProducts() {
        return false;
    }
}
