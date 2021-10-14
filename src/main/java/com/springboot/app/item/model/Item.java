package com.springboot.app.item.model;

import com.springboot.app.commonslib.model.entity.Product;



public class Item {
    
    private Product product;
    private Integer quantity;


    public Item(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Item() {
    }

    public Double getTotal(){
        return product.getPrice() * quantity.doubleValue();
    }


    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
