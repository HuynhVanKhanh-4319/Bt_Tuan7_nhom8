package com.example.nhom8.controller;

import com.example.nhom8.entity.Product;
import com.example.nhom8.service.CategoryService;
import com.example.nhom8.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequestMapping("admin/products")
@Controller
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("")
    public String index(Model model)
    {
        model.addAttribute("product", productService.getAllProducts());
        return "admin/products/index";
    }
    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("product", new Product());
        model.addAttribute("Category", categoryService.getAllTheloai() );
        return "admin/products/create";
    }
    @PostMapping("/create")
    public String create(@Valid Product newProduct,
                         @RequestParam MultipartFile imageProduct,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors())
        {
            model.addAttribute("product", newProduct);
            model.addAttribute("Category", categoryService.getAllTheloai() );
            return "admin/products/create";
        }
        if(imageProduct != null && imageProduct.getSize() > 0)
        {
            try {
                File saveFile = new ClassPathResource("static/image").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newImageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.addProduct(newProduct);
        return "redirect:/admin/products";
    }
    @GetMapping("/edit/{id}")
    public String editproduct(@PathVariable Long id, Model model)
    {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("Category", categoryService.getAllTheloai() );
        return "admin/products/edit";
    }
    @PostMapping("/update/{id}")
    public String edit(@Valid Product product,
                       @RequestParam MultipartFile imageProduct,
                       BindingResult result, @PathVariable Long id)
    {
        if (result.hasErrors()) {
            product.setId(id);
            return "admin/products/edit";
        }
        if(imageProduct != null && imageProduct.getSize() > 0)
        {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +
                        product.getImage());
                Files.copy(imageProduct.getInputStream(), path,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.updateProduct(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }
}