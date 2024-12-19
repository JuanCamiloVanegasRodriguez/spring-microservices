package bored.juan.microservices.products.service;

import bored.juan.microservices.products.entity.Category;
import bored.juan.microservices.products.entity.Product;
import bored.juan.microservices.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product product = Product.builder()
                .id(1L)
                .name("Call of Duty")
                .description("The same game but with a different name")
                .stock(10.0)
                .price(80.0)
                .status("AVAILABLE")
                .category(Category.builder().id(1L).name("Shotter").build())
                .createdAt(new Date())
                .build();
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
    }

    @Test
    void whenValidId_thenProductShouldBeFound() {
        Long id = 1L;

        Product found = productService.getProduct(id);

        assertEquals(found.getId(), id);
    }

    @Test
    void whenValidUpdateStock_thenProductShouldBeUpdated() {
        Long id = 1L;
        Double quantity = 5.0;

        Product updated = productService.updateStock(id, quantity);

        assertEquals(15.0, updated.getStock());
    }
}