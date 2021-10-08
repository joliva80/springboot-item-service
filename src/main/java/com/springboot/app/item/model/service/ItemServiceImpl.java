package com.springboot.app.item.model.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.springboot.app.item.model.Item;
import com.springboot.app.item.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service("restTemplateService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private RestTemplate restClient;

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays.asList(restClient.getForObject("http://localhost:8001/products", Product[].class));
        List<Item> items = products.stream()
                                    .map(p -> new Item(p, 1)) // lambda to apply transformation formula for each element in the list, 1 by default
                                    .collect(Collectors.toList());

        return items;
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        Map<String, String>  uriVariables = new HashMap<String, String>();
        uriVariables.put("id", id.toString());
        Product product = restClient.getForObject("http://localhost:8001/products/{id}", Product.class, uriVariables);
        Item item = new Item(product, quantity);

        return item;
    }
    
}
