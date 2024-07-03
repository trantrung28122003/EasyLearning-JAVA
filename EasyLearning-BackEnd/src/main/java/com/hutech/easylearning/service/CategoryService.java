package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.request.CategoryCreationRequest;
import com.hutech.easylearning.dto.request.CategoryUpdateRequest;
import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    CourseDetailService courseDetailService;
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Transactional
    public Category createCategory(CategoryCreationRequest request) {
        var currentUserInfo = userService.getMyInfo();
        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .imageLink(request.getImageLink())
                .sortOrder(request.getSortOrder())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUserInfo.getId())
                .isDeleted(false)
                .build();
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(String categoryId, CategoryUpdateRequest request) {
        var currentUserInfo = userService.getMyInfo();
        var categoryById = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        categoryById.setCategoryName(request.getCategoryName());
        categoryById.setImageLink(request.getImageLink());
        categoryById.setSortOrder(request.getSortOrder());
        categoryById.setDateChange(LocalDateTime.now());
        categoryById.setChangedBy(currentUserInfo.getId());
        return categoryRepository.save(categoryById);
    }

    @Transactional
    public void deleteCategory(String categoryId) {
        courseDetailService.deleteCourseDetailsByCategoryId(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public void softDeleteCategory(String categoryId) {
        courseDetailService.softDeleteCourseDetailsByCategoryId(categoryId);

        Category category = getCategoryById(categoryId);
        category.setDeleted(true);

        categoryRepository.save(category);
    }

    @Transactional
    public List<Category> getCategoriesByNames(List<String> categoryNames) {
        return categoryRepository.findCategoriesByCategoryNameIn(categoryNames);
    }
}
