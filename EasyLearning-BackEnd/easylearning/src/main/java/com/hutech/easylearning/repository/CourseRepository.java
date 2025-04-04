package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findTop4ByOrderByRegisteredUsersDesc();
    List<Course> findByCourseNameContainingIgnoreCase(String courseName);
    List<Course> findCoursesByIsFree(boolean free);
}
