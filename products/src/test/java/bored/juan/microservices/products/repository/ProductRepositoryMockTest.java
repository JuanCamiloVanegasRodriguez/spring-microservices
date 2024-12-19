package bored.juan.microservices.products.repository;

import bored.juan.microservices.products.entity.Category;
import bored.juan.microservices.products.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;


@DataJpaTest
class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void whenFindyByCategory_thenReturnListProduct() {
        //Arrange
        Product product = Product.builder()
                .name("Call of Duty")
                .description("The same game but with a different name")
                .stock(10.0)
                .price(80.0)
                .status("AVAILABLE")
                .category(Category.builder().id(1L).name("Shotter").build())
                .createdAt(new Date())
                .build();
        productRepository.save(product);

        //Act
        List<Product> productsFound = productRepository.findByCategory(product.getCategory());

        //Assert
        Assertions.assertEquals(4, productsFound.size());
    }
}