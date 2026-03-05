package com.assignment14.ProductInventoryAPI.service;

import com.assignment14.ProductInventoryAPI.entity.Product;
import com.assignment14.ProductInventoryAPI.exceptions.InvalidSkuFormatException;
import com.assignment14.ProductInventoryAPI.exceptions.ProductNotFoundException;
import com.assignment14.ProductInventoryAPI.exceptions.SkuAlreadyExistsException;
import com.assignment14.ProductInventoryAPI.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository repository;
    
    private void validateSkuFormat(String sku) {
        if (sku == null || !sku.matches("^SKU-[A-Z0-9]{8}$")) {
            throw new InvalidSkuFormatException("SKU must start with SKU- followed by 8 alphanumeric characters.");
        }
    }
    
    public Product createProduct(Product product) {
        validateSkuFormat(product.getSku());
        if (repository.existsBySku(product.getSku())) {
            throw new SkuAlreadyExistsException("SKU " + product.getSku() + " already exists.");
        }
        Product savedProduct = repository.save(product);
        log.info("Product created with ID: {} and SKU: {}", savedProduct.getId(), savedProduct.getSku());
        return savedProduct;
    }
    
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }
    
    public Product updateProduct(Long id, Product updatedDetails) {
        Product existingProduct = getProductById(id);
        
        if (!existingProduct.getSku().equals(updatedDetails.getSku())) {
            throw new InvalidSkuFormatException("The SKU of an existing product cannot be changed.");
        }
        
        existingProduct.setName(updatedDetails.getName());
        existingProduct.setDescription(updatedDetails.getDescription());
        existingProduct.setPrice(updatedDetails.getPrice());
        existingProduct.setQuantity(updatedDetails.getQuantity());
        existingProduct.setStatus(updatedDetails.getStatus());
        
        log.info("Product with ID: {} updated successfully", id);
        return repository.save(existingProduct);
    }
    
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Cannot delete. Product not found with ID: " + id);
        }
        repository.deleteById(id);
        log.info("Product with ID: {} deleted", id);
    }
}
