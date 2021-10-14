package com.springboot.app.item.model.entity;

import java.util.Date;

/**
 * deprecated class as we are using the import import com.springboot.app.commonslib.model.entity.Product;
 */
public class Product {
   
    private Long id;
    private String name; // same name as in DB
    private Double price;
    private Date creationDate;
    private Integer port;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


}
