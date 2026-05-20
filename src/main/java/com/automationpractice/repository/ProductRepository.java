package com.automationpractice.repository;

import com.automationpractice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Product.Category category);
    List<Product> findByFeaturedTrue();
    List<Product> findByAvailableTrue();
    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findInStock();

    @Query("SELECT p FROM Product p ORDER BY p.rating DESC")
    List<Product> findTopRated();
}
