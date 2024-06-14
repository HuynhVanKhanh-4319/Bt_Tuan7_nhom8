package com.example.nhom8.service;

import com.example.nhom8.entity.Product;
import com.example.nhom8.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> GetAll(){
        return productRepository.findAll();
    }

}
