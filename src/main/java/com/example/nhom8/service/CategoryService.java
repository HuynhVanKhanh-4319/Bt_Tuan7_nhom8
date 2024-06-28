package com.example.nhom8.service;

import com.example.nhom8.entity.Category;
import com.example.nhom8.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllTheloai() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getTheloaiById(Long id) {
        return categoryRepository.findById(id);
    }

    public void addTheloai(Category theLoai) {
        categoryRepository.save(theLoai);
    }

    public void updateTheloai(@NotNull Category theLoai) {
        Category Theloai = categoryRepository.findById(theLoai.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " +
                        theLoai.getId() + " does not exist."));
        Theloai.setName(theLoai.getName());
        categoryRepository.save(Theloai);
    }
    public void deleteTheloaiById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }
}
