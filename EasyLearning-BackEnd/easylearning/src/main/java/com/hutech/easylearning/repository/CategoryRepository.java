package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findCategoriesByCategoryNameIn(List<String> categoryNames);
    List<Category> findTop4BySortOrderInOrderBySortOrderAsc(List<Integer> sortOrders);
}
