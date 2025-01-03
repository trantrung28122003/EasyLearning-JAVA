package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.request.*;
import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.service.CategoryService;
import com.hutech.easylearning.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ApiResponse<Category> createCategory(@Valid CategoryCreationRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        System.out.println("Received categoryName: " + request.getCategoryName());
        System.out.println("Received imageLink: " + request.getImageLink());

        // Kiểm tra nếu isDeleted không có giá trị thì gán mặc định là false
        if (request.getIsDeleted() == null) {
            request.setIsDeleted(false);
        }
        return ApiResponse.<Category>builder()
                .result(categoryService.createCategory(request, file))
                .build();
    }

    @PutMapping("/update/{categoryId}")
    public ApiResponse<Category> updateCategory(@PathVariable("categoryId") String categoryId ,@RequestBody @Valid CategoryUpdateRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        return ApiResponse.<Category>builder()
                .result(categoryService.updateCategory(categoryId, request, file))
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
