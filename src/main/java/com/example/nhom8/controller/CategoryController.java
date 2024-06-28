package com.example.nhom8.controller;

import com.example.nhom8.entity.Category;
import com.example.nhom8.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;
    @GetMapping("/categories/create")
    public String showAddForm(Model model) {
        model.addAttribute("categories", new Category());
        return "/categories/create";
    }
    @PostMapping("/categories/create")
    public String addTheLoai(@Valid Category theLoai, BindingResult result) {
        if (result.hasErrors()) {
            return "/categories/create";
        }
        categoryService.addTheloai(theLoai);
        return "redirect:/categories";
    }
    @GetMapping("/categories")
    public String listTheLoai(Model model) {
        List<Category> theLoai = categoryService.getAllTheloai();
        model.addAttribute("categories",  theLoai);
        return "/categories/index";
    }
    @GetMapping("/categories/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Category theLoai = categoryService.getTheloaiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:"
                        + id));
        model.addAttribute("categories", theLoai);
        return "/categories/edit";
    }
    @PostMapping("/categories/update/{id}")
    public String updateTheLoai(@PathVariable("id") Long id, @Valid Category theLoai,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            theLoai.setId(id);
            return "/categories/edit";
        }
        categoryService.updateTheloai(theLoai);
        model.addAttribute("categories", categoryService.getAllTheloai());
        return "redirect:/categories";
    }
    @GetMapping("/categories/delete/{id}")
    public String deletetheloai(@PathVariable("id") Long id, Model model) {
        Category theLoai = categoryService.getTheloaiById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:"
                        + id));
        categoryService.deleteTheloaiById(id);
        model.addAttribute("categories", categoryService.getAllTheloai());
        return "redirect:/categories";
    }
}
