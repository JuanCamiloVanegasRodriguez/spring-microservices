package bored.juan.microservices.shopping.client;

import bored.juan.microservices.shopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "products")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id);

    @PutMapping("/products/{id}/stock")
    ResponseEntity<Product> updateStock(@PathVariable(value = "id") Long id,
                                        @RequestParam(name = "quantity") Double quantity);
}
