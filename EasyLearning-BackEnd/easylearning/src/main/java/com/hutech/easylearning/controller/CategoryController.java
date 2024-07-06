package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.request.*;
import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.service.CategoryService;
import com.hutech.easylearning.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream()
                .filter(category -> !category.isDeleted())
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ApiResponse<Category> createCategory(@RequestBody @Valid CategoryCreationRequest request) {
        return ApiResponse.<Category>builder()
                .result(categoryService.createCategory(request))
                .build();
    }

    @PutMapping("/update/{categoryId}")
    public ApiResponse<Category> updateCategory(@PathVariable("categoryId") String categoryId ,@RequestBody @Valid CategoryUpdateRequest request) {
        return ApiResponse.<Category>builder()
                .result(categoryService.updateCategory(categoryId, request))
                .build();
    }

    @DeleteMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") String categoryId) {
        categoryService.deleteCategory(categoryId);
        return "Category has been deleted";
    }

    @DeleteMapping("/softDelete/{categoryId}")
    public String softDeleteCategory(@PathVariable("categoryId") String categoryId) {
        categoryService.softDeleteCategory(categoryId);
        return "Category has been soft deleted";
    }


}
