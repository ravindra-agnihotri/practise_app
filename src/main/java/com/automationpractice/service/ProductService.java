package com.automationpractice.service;

import com.automationpractice.model.Product;
import com.automationpractice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(Product.Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updated) {
        return productRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setDescription(updated.getDescription());
            p.setPrice(updated.getPrice());
            p.setStock(updated.getStock());
            p.setCategory(updated.getCategory());
            p.setFeatured(updated.isFeatured());
            p.setAvailable(updated.isAvailable());
            p.setRating(updated.getRating());
            return productRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public long count() {
        return productRepository.count();
    }
}
