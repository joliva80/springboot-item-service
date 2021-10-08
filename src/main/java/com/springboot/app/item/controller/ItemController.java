package com.springboot.app.item.controller;

import java.util.List;

import com.springboot.app.item.model.Item;
import com.springboot.app.item.model.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ItemController {
    
    @Autowired
    @Qualifier("feignService") // Select the bean for the implementation, just in case there is no one by default. This is optional.
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getAllItems(@RequestParam(name="req-parameter", required = false) String reqParam,  /* All the filter params come from application.yml */
                                  @RequestHeader(name="req-token-header", required = false) String reqTokenHeader){
        System.out.println("req-parameter: " + reqParam);                                      
        System.out.println("req-token-header:" + reqTokenHeader);
        return itemService.findAll();
    }

    @GetMapping("/items/{id}/quantity={quantity}")
    public Item getItemsById(@PathVariable Long id, @PathVariable Integer quantity){
        return itemService.findById(id, quantity);
    }
}
