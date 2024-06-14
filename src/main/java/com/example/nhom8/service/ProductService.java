package com.example.nhom8.service;

import com.example.nhom8.entity.Product;
import com.example.nhom8.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> GetAll(){
        return productRepository.findAll();
    }

    public Optional<Product> get(int id) {
        return productRepository.findById(id);
    }
    // Add a new product to the database
    public Product add(Product product) {
        return productRepository.save(product);
    }
    // Update an existing product
    public Product edit(@NotNull Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImage(product.getImage());
        return productRepository.save(existingProduct);
    }
    // Delete a product by its id
    public void delete(int id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }


}
