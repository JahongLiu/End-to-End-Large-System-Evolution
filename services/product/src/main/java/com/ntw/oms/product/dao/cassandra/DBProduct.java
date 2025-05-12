package com.ntw.oms.product.dao.cassandra;

import com.ntw.oms.product.entity.Product;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("Product")
public class DBProduct {
    @PrimaryKey
    private String id;
    private String name;
    private Float price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + (id == null ? "null" : "\"" + id + "\"") + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"price\":" + (price == null ? "null" : "\"" + price + "\"") + ", " +
                "}";
    }

    public static DBProduct createProduct(Product product) {
        DBProduct dbProduct = new DBProduct();
        dbProduct.setId(product.getId());
        dbProduct.setName(product.getName());
        dbProduct.setPrice(product.getPrice());
        return dbProduct;
    }

    public Product getProduct() {
        Product product = new Product();
        product.setId(getId());
        product.setName(getName());
        product.setPrice(getPrice());
        return product;
    }
}
