package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDiscountRepository extends JpaRepository<CourseDiscount, String> {
    List<CourseDiscount> findAllByCourseId(String courseId);

    @Query("SELECT DISTINCT cd.course FROM CourseDiscount cd")
    List<Course> findDistinctCoursesWithDiscount();
}
