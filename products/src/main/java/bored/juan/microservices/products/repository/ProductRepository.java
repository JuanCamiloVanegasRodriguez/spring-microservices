package bored.juan.microservices.products.repository;

import bored.juan.microservices.products.entity.Category;
import bored.juan.microservices.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByCategory(Category category);
}
