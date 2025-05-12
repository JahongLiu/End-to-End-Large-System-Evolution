package com.ntw.oms.product.dao;

import com.ntw.oms.product.entity.Product;

import java.util.List;

public interface ProductDao {

    public List<Product> getProducts();

    public List<Product> getProducts(List<String> ids);

    public Product getProduct(String id);

    public boolean addProduct(Product product);

    Product modifyProduct(Product product);

    public boolean removeProduct(String id);

    public boolean removeProducts();
}
