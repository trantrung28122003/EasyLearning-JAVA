package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findCategoriesByCategoryNameIn(List<String> categoryNames);
    List<Category> findTop4BySortOrderInOrderBySortOrderAsc(List<Integer> sortOrders);


    @Query("SELECT c FROM Category c " +
            "JOIN CourseDetail cd ON c.id = cd.category.id " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(cd.course.id) DESC")
    List<Category> findTop4CategoriesWithMostCourses(Pageable pageable);

}
