package com.springboot.app.item.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.springboot.app.item.model.Item;
import com.springboot.app.item.model.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@RestController
public class ItemController {
    
    //@Autowired
    //private CircuitBreakerFactory cbFactory;

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

    /*
    // Circuit break with coding CircuitBreakFactory library
    @GetMapping("/items/{id}/quantity={quantity}")
    public Item getItemsById(@PathVariable Long id, @PathVariable Integer quantity){
        //return itemService.findById(id, quantity);
        return cbFactory.create("ItemsCircuitBreaker")  // cbItems is the name we have set for the Items Circuit Breaker
                        .run(() -> itemService.findById(id, quantity));
    }
    */

    /*
    // Circuit break with annotations
    @CircuitBreaker(name="ItemsCircuitBreaker") // , fallbackmethod()) alternative method in case of open circuit
    @GetMapping("/items/{id}/quantity={quantity}")
    public Item getItemsByIdWithCircuitBreakAnnotation(@PathVariable Long id, @PathVariable Integer quantity){
        return itemService.findById(id, quantity);
    }
    */

    // Timeout + CircuitBreak with annotations
    @TimeLimiter(name="ItemsCircuitBreaker")
    @CircuitBreaker(name="ItemsCircuitBreaker") // , fallbackmethod()) alternative method in case of open circuit
    @GetMapping("/items/{id}/quantity={quantity}")
    public CompletableFuture<Item> getItemsByIdWithCircuitBreakAnnotation(@PathVariable Long id, @PathVariable Integer quantity){
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity)) ;
    }
}
