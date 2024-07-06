package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, String> {
    List<CourseDetail> findCourseDetailByCourseId(String courseId);
    List<CourseDetail> findCourseDetailByCategoryId(String courseId);
}
