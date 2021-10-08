package com.springboot.app.item.clients;

import java.util.List;

import com.springboot.app.item.model.Product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface IProductRestClient {
    
    @GetMapping("/products")
    public List<Product> getAllProducts();

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id);
}
