package com.hutech.easylearning.service;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseDetail;
import com.hutech.easylearning.repository.CourseDetailRepository;
import com.hutech.easylearning.repository.CourseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDetailService {
    final CourseDetailRepository courseDetailRepository;



    @Transactional(readOnly = true)
    public List<CourseDetail> getAllCourseDetails() {
        return courseDetailRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CourseDetail getCourseDetailById(String id) {
        return courseDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseDetail not found with id: " + id));
    }

    @Transactional
    public CourseDetail createCourseDetail(CourseDetail courseDetail) {
        return courseDetailRepository.save(courseDetail);
    }

    @Transactional
    public CourseDetail updateCourseDetail(CourseDetail courseDetail) {
        return courseDetailRepository.save(courseDetail);
    }

    @Transactional
    public void deleteCourseDetail(String id) {
        courseDetailRepository.deleteById(id);
    }

    @Transactional
    public List<CourseDetail> getCourseDetailsByCourseId(String courseId) {
        return courseDetailRepository.findCourseDetailByCourseId(courseId);
    }

    @Transactional
    public List<CourseDetail> getCourseDetailsByCategoryId(String categoryId) {
        return courseDetailRepository.findCourseDetailByCategoryId(categoryId);
    }

    @Transactional
    public void deleteCourseDetailsByCourseId(String courseId) {
        var courseDetailByCourseId = courseDetailRepository.findCourseDetailByCourseId(courseId);
        for(var courseDetail : courseDetailByCourseId)
        {
            courseDetailRepository.deleteById(courseDetail.getId());
        }
    }

    @Transactional
    public void deleteCourseDetailsByCategoryId(String categoryId) {
        var courseDetailsByCategoryId = courseDetailRepository.findCourseDetailByCategoryId(categoryId);
        for(var courseDetail : courseDetailsByCategoryId)
        {
            courseDetailRepository.deleteById(courseDetail.getId());
        }
    }
    @Transactional
    public void softDeleteCourseDetailsByCategoryId(String categoryId) {
        var courseDetailsByCategoryId = courseDetailRepository.findCourseDetailByCategoryId(categoryId);
        for(var courseDetail : courseDetailsByCategoryId)
        {
            courseDetail.setDeleted(true);
            courseDetail.setDateChange(LocalDateTime.now());
            courseDetailRepository.save(courseDetail);
        }
    }

    @Transactional
    public void softDeleteCourseDetailsByCourseId(String CourseId) {
        var courseDetailsByCourseId = courseDetailRepository.findCourseDetailByCourseId(CourseId);
        for(var courseDetail : courseDetailsByCourseId)
        {
            courseDetail.setDeleted(true);
            courseDetail.setDateChange(LocalDateTime.now());
            courseDetailRepository.save(courseDetail);
        }
    }

    @Transactional
    public void restoreCourseDetailsByCategoryId(String categoryId) {
        var courseDetailsByCategoryId = courseDetailRepository.findCourseDetailByCategoryId(categoryId);
        for(var courseDetail : courseDetailsByCategoryId)
        {
            courseDetail.setDeleted(false);
            courseDetail.setDateChange(LocalDateTime.now());
            courseDetailRepository.save(courseDetail);
        }
    }

    @Transactional
    public void restoreCourseDetailsByCourseId(String CourseId) {
        var courseDetailsByCourseId = courseDetailRepository.findCourseDetailByCourseId(CourseId);
        for(var courseDetail : courseDetailsByCourseId)
        {
            courseDetail.setDeleted(false);
            courseDetail.setDateChange(LocalDateTime.now());
            courseDetailRepository.save(courseDetail);
        }
    }
}

