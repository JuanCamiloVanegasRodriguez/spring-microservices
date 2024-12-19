package bored.juan.microservices.products.controller;

import bored.juan.microservices.products.entity.Category;
import bored.juan.microservices.products.entity.Product;
import bored.juan.microservices.products.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<Product> products;
        if (categoryId == null) {
            products = productService.listAllProducts();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        Product productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product,
                                                 @PathVariable(value = "id") Long id,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        product.setId(id);
        Product productUpdated = productService.updateProduct(product);
        if (productUpdated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") Long id) {
        Product productDeleted = productService.deleteProduct(id);
        if (productDeleted == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productDeleted);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable(value = "id") Long id,
                                               @RequestParam(name = "quantity") Double quantity) {
        Product productUpdated = productService.updateStock(id, quantity);
        if (productUpdated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productUpdated);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getAllErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getCode(), err.getDefaultMessage());
                    return error;
                }).toList();
        ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
