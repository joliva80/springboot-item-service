package com.springboot.app.item.model.service;

import java.util.List;

import com.springboot.app.item.model.Item;
import com.springboot.app.commonslib.model.entity.Product;

public interface ItemService {
    
    public List<Item> findAll();
    public Item findById(Long id, Integer quantity);
    public Product save(Product product);
    public Product update(Product product, Long id);
    public void delete(Long id);
}