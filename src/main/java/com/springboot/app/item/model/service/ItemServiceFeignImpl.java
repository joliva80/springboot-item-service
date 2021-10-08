package com.springboot.app.item.model.service;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.app.item.clients.IProductRestClient;
import com.springboot.app.item.model.Item;
import com.springboot.app.item.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("feignService")
@Primary // code inection by default for the implementation of the service
public class ItemServiceFeignImpl implements ItemService {

    @Autowired
    private IProductRestClient feignClient;

    @Override
    public List<Item> findAll() {
        List<Item> items = feignClient.getAllProducts().stream()
                                      .map(p -> new Item(p, 1)) // lambda to apply transformation formula for each element in the list, 1 by default
                                      .collect(Collectors.toList());
        return items;
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        Product product = feignClient.getProductById(id);
        Item item = new Item(product, quantity);
        return item;
    }
    
}
