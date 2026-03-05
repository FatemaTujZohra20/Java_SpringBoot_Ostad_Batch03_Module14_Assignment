package com.assignment14.ProductInventoryAPI.repository;

import com.assignment14.ProductInventoryAPI.entity.Product;
import com.assignment14.ProductInventoryAPI.entity.ProductStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // --- DERIVED QUERIES ---
    // Spring generates the SQL based on the method name
    
    /**
     * Checks if a product with the given SKU already exists in the database.
     * Used for Task 2 to ensure SKU uniqueness.
     * * @param sku The Stock Keeping Unit string
     * @return true if exists, false otherwise
     */
    boolean existsBySku(String sku);
    
    // Find products by status (e.g., all ACTIVE products)
    List<Product> findByStatus(ProductStatus status);
    
    // Find products where name contains a string, ignoring case
    List<Product> findByNameContainingIgnoreCase(String name);
    
    // Find products with quantity less than a certain amount (Low Stock)
    List<Product> findByQuantityLessThan(Integer threshold);
    
    // --- JPQL QUERIES ---
    // Custom queries written using Entity and Field names, not Table names
    
    // Find products within a price range
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    // Custom search for specific status and stock availability
    @Query("SELECT p FROM Product p WHERE p.status = :status AND p.quantity > 0")
    List<Product> findAvailableByStatus(@Param("status") ProductStatus status);
}
