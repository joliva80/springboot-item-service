package com.springboot.app.item.clients;

import java.util.List;

import com.springboot.app.commonslib.model.entity.Product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service")
public interface IProductRestClient {
    
    @GetMapping("/products")
    public List<Product> getAllProducts();

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id);

    @PostMapping("/products")
    public Product save(@RequestBody Product product);

    @PutMapping("/products/{id}")
    public Product update(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id);
}
