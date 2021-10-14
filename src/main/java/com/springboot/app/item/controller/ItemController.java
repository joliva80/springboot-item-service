package com.springboot.app.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import com.springboot.app.item.model.Item;
import com.springboot.app.commonslib.model.entity.Product;
import com.springboot.app.item.model.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@RestController
@RefreshScope
public class ItemController {
    
    private static Logger logger = Logger.getLogger(ItemController.class.getName());

    //@Autowired
    //private CircuitBreakerFactory cbFactory; // only for hystrix, not for resilience4j

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("feignService") // Select the bean for the implementation, just in case there is no one by default. This is optional.
    //@Qualifier("restTemplateService") // Select the bean for the implementation, just in case there is no one by default. This is optional.
    private ItemService itemService;

    @Value("${configuration.environmentDescription}") // Fetch custom configuration from spring config sever
    private String environmentDescription;



    @GetMapping("/config")
    public ResponseEntity<?> getConfig(){

        logger.info(environmentDescription);

        Map<String, String> configMap = new HashMap<>();
        configMap.put("environmentDescription", environmentDescription);

        if( environment.getActiveProfiles().length > 0 ){
            configMap.put("environment", environment.getActiveProfiles()[0]);
        }
        
        return new ResponseEntity<Map<String, String>>(configMap, HttpStatus.OK);
    }



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


    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product){
        Product newProduct = itemService.save(product); // Call product service api
        return newProduct;
    }


    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@RequestBody Product product, @PathVariable Long id)
    {
        Product newProduct = itemService.update(product, id); // Call product service api
        return newProduct;
    }


    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id){
        itemService.delete(id);
    }
}
