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
import org.springframework.data.domain.PageRequest;
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
        // Lấy thông tin người dùng hiện tại
        var currentUserInfo = userService.getMyInfo();

        // Kiểm tra giá trị nhận được từ request
        System.out.println("Received categoryName: " + request.getCategoryName());
        System.out.println("Received imageLink: " + request.getImageLink());

        String imageLink = "Default"; // Đặt giá trị mặc định cho imageLink

        // Nếu có file thì upload file và lấy link ảnh
        if (file != null) {
            imageLink = uploaderService.uploadFile(file);
        } else if (request.getImageLink() != null && !request.getImageLink().isEmpty()) {
            imageLink = request.getImageLink(); // Nếu có imageLink từ request, sử dụng link này
        }

        // Kiểm tra imageLink sau khi upload file hoặc lấy từ request
        System.out.println("Image link after file upload or provided imageLink: " + imageLink);

        // Tạo category từ dữ liệu nhận được
        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .imageLink(imageLink)
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0) // Đảm bảo có giá trị sortOrder
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUserInfo.getId())
                .isDeleted(request.getIsDeleted() != null ? request.getIsDeleted() : false)
                .build();

        // Lưu vào CSDL và trả về
        return categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Category updateCategory(String categoryId, CategoryUpdateRequest request, MultipartFile file) {
        var currentUserInfo = userService.getMyInfo();
        var categoryById = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        if (file != null) {
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

        return categoryRepository.findTop4CategoriesWithMostCourses(PageRequest.of(0, 5));
    }

    public List<CategoryWithCourseResponse> getAllCategoryWithCourse() {
        List<CategoryWithCourseResponse> categoryWithCourseResponses = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for(var category :categories)
        {
            List<Course> coursesInCategory = new ArrayList<>();
            var courseDetails = courseDetailRepository.findCourseDetailByCategoryId(category.getId());
            for (CourseDetail courseDetail : courseDetails) {
               for(var course : courseRepository.findAll())
               {
                   if(course.getId().equals(courseDetail.getCourseId())) {
                       coursesInCategory.add(course);
                   }
               }
            }
            //List<Course> courses = courseRepository.findCoursesByIdIn(courseIds);
            CategoryWithCourseResponse categoryWithCourseResponse = CategoryWithCourseResponse.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .courses(coursesInCategory)
                    .build();
            categoryWithCourseResponses.add(categoryWithCourseResponse);
            System.out.print("Category: " + category.getCategoryName());
            System.out.print(" khóa học của category " + category.getCategoryName() + " là: ");
            for (Course course : coursesInCategory) {
                System.out.print(course.getCourseName() + ", ");
            }
            System.out.println();
        }
        return categoryWithCourseResponses;
    }
    public CategoryWithCourseResponse getCoursesByCategory(String categoryId) {

        List<Course> coursesInCategory = new ArrayList<>();
        var courseDetails = courseDetailRepository.findCourseDetailByCategoryId(categoryId);
        for (CourseDetail courseDetail : courseDetails) {
            for(var course : courseRepository.findAll())
            {
                if(course.getId().equals(courseDetail.getCourseId())) {
                    coursesInCategory.add(course);
                }
            }
        }
            //List<Course> courses = courseRepository.findCoursesByIdIn(courseIds);
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        CategoryWithCourseResponse categoryWithCourseResponse = CategoryWithCourseResponse.builder()
                .id(categoryId)
                .categoryName(category.getCategoryName())
                .courses(coursesInCategory)
                .build();

        return categoryWithCourseResponse;
    }



}
