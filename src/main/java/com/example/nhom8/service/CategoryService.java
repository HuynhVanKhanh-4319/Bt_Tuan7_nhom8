package com.example.nhom8.service;






import com.example.nhom8.entity.Category;
import com.example.nhom8.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> GetAll() {
        return categoryRepository.findAll();
    }

}
