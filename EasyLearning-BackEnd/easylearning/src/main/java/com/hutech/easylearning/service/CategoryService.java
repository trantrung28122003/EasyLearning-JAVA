package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.CategoryWithCourseResponse;
import com.hutech.easylearning.dto.request.CategoryCreationRequest;
import com.hutech.easylearning.dto.request.CategoryUpdateRequest;
import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseDetail;
import com.hutech.easylearning.repository.CategoryRepository;
import com.hutech.easylearning.repository.CourseDetailRepository;
import com.hutech.easylearning.repository.CourseRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    CourseDetailService courseDetailService;

    @Autowired
    private UploaderService uploaderService;
    @Autowired
    private CourseDetailRepository courseDetailRepository;
    @Autowired
    private CourseRepository courseRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Category createCategory(CategoryCreationRequest request, MultipartFile file) {
        var currentUserInfo = userService.getMyInfo();
        String imageLink ="Default";
        if(file != null)
        {
            imageLink = uploaderService.uploadFile(file);
        }
        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .imageLink(imageLink)
                .sortOrder(request.getSortOrder())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUserInfo.getId())
                .isDeleted(false)
                .build();
        return categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Category updateCategory(String categoryId, CategoryUpdateRequest request, MultipartFile file) {
        var currentUserInfo = userService.getMyInfo();
        var categoryById = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        if(file != null)
        {
            String imageLink = uploaderService.uploadFile(file);
            categoryById.setImageLink(imageLink);
        }
        categoryById.setCategoryName(request.getCategoryName());
        categoryById.setSortOrder(request.getSortOrder());
        categoryById.setDateChange(LocalDateTime.now());
        categoryById.setChangedBy(currentUserInfo.getId());
        return categoryRepository.save(categoryById);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteCategory(String categoryId) {
        courseDetailService.deleteCourseDetailsByCategoryId(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void softDeleteCategory(String categoryId) {
        courseDetailService.softDeleteCourseDetailsByCategoryId(categoryId);
        Category category = getCategoryById(categoryId);
        category.setDeleted(true);
        category.setDateChange(LocalDateTime.now());
        categoryRepository.save(category);
    }

    @Transactional
    public List<Category> getCategoriesByNames(List<String> categoryNames) {
        return categoryRepository.findCategoriesByCategoryNameIn(categoryNames);
    }

    public List<Category> findTop4BySortOrderNotNull() {
        List<Integer> sortOrder = Arrays.asList(1, 2, 3, 4);
        return categoryRepository.findTop4BySortOrderInOrderBySortOrderAsc(sortOrder);
    }

    public List<CategoryWithCourseResponse> getAllCategoryWithCourse() {
        List<CategoryWithCourseResponse> categoryWithCourseResponses = new ArrayList<>();
        Set<String> courseIdSet = new HashSet<>();
        List<Category> categories = categoryRepository.findAll();

        for(var category :categories)
        {
            var courseDetails = courseDetailRepository.findCourseDetailByCategoryId(category.getId());
            for (CourseDetail courseDetail : courseDetails) {
                courseIdSet.add(courseDetail.getCourseId());
            }
            List<String> courseIds = new ArrayList<>(courseIdSet);
            List<Course> courses = courseRepository.findAllByIdIn(courseIds);
            CategoryWithCourseResponse categoryWithCourseResponse = CategoryWithCourseResponse.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .courses(courses)
                    .build();
            categoryWithCourseResponses.add(categoryWithCourseResponse);
        }
        return categoryWithCourseResponses;
    }

}
