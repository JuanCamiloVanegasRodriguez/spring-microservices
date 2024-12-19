package bored.juan.microservices.products.service;

import bored.juan.microservices.products.entity.Category;
import bored.juan.microservices.products.entity.Product;
import bored.juan.microservices.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreatedAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if (productDB == null) {
            return null;
        }

        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setStock(product.getStock());
        productDB.setPrice(product.getPrice());
        productDB.setStatus("UPDATED");
        productDB.setCategory(product.getCategory());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);
        if (productDB == null) {
            return null;
        }

        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if (productDB == null) {
            return null;
        }

        productDB.setStock(productDB.getStock() + quantity);
        return productRepository.save(productDB);
    }
}
