package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.request.CourseCreationRequest;
import com.hutech.easylearning.dto.request.CourseUpdateRequest;
import com.hutech.easylearning.entity.*;
import com.hutech.easylearning.enums.CourseType;
import com.hutech.easylearning.repository.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CourseDetailService courseDetailService;

    @Autowired
    TrainerDetailService trainerDetailService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CourseDetailRepository courseDetailRepository;


    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    @Transactional
    public Course createCourse(CourseCreationRequest request) {
        String imageLink = "https://easylearning.blob.core.windows.net/images-videos/default_course.png";
        if(request.getImageUrl() != null) {
            //imageLink = fileService.saveFile(courseViewModel.getImage());
        }

        var currentUserInfo  =  userService.getMyInfo();

        String instructor = request.getInstructor() != null ? request.getInstructor() : currentUserInfo.getFullName();
        Course course = Course.builder()
                .courseName(request.getCourseName())
                .courseType(CourseType.valueOf(request.getCourseType()))
                .courseDescription(request.getCourseDescription())
                .coursePrice(request.getCoursePrice())
                .requirements(request.getRequirements())
                .courseContent(request.getCourseContent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .registrationDeadline(request.getRegistrationDeadline())
                .maxAttendees(request.getMaxAttendees())
                .instructor(instructor)
                .imageUrl(imageLink)
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUserInfo.getId())
                .isDeleted(false)
                .build();

        var categoriesByNames = categoryService.getCategoriesByNames(request.getCategories());
        Course savedCourse = courseRepository.save(course);
        for (var category : categoriesByNames) {
            CourseDetail courseDetail = CourseDetail.builder()
                    .courseId(course.getId())
                    .categoryId(category.getId())
                    .dateCreate(LocalDateTime.now())
                    .dateChange(LocalDateTime.now())
                    .isDeleted(false)
                    .changedBy(currentUserInfo.getId())
                    .build();
            courseDetailService.createCourseDetails(courseDetail);
        }

        TrainerDetail trainerDetail = TrainerDetail.builder()
                .courseId(course.getId())
                .userId(currentUserInfo.getId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .isDeleted(false)
                .changedBy(currentUserInfo.getId())
                .build();

        trainerDetailService.createTrainerDetail(trainerDetail);
        return savedCourse;
    }

    @Transactional
    public Course updateCourse(String courseId, CourseUpdateRequest request) {
        var courseById = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        var currentUserInfo  =  userService.getMyInfo();
        String imageLink = request.getCurrentImageUrl();

        if (request.getNewImageUrl() != null) {
            //imageLink = fileService.saveFile(courseViewModel.getImage());
        }

        courseById.setCourseName(request.getCourseName());
        courseById.setCourseType(CourseType.valueOf(request.getCourseType()));
        courseById.setCourseDescription(request.getCourseDescription());
        courseById.setCoursePrice(request.getCoursePrice());
        courseById.setRequirements(request.getRequirements());
        courseById.setCourseContent(request.getCourseContent());
        courseById.setStartDate(request.getStartDate());
        courseById.setEndDate(request.getEndDate());
        courseById.setRegistrationDeadline(request.getRegistrationDeadline());
        courseById.setMaxAttendees(request.getMaxAttendees());
        courseById.setInstructor(request.getInstructor());
        courseById.setImageUrl(imageLink);
        courseById.setDateChange(LocalDateTime.now());
        courseById.setChangedBy(currentUserInfo.getId());

        courseDetailService.deleteCourseDetailsByCourseId(courseId);

        var categoriesByNames = categoryService.getCategoriesByNames(request.getCategories());
        for (var category : categoriesByNames) {
            CourseDetail courseDetail = CourseDetail.builder()
                    .courseId(courseById.getId())
                    .categoryId(category.getId())
                    .dateCreate(LocalDateTime.now())
                    .dateChange(LocalDateTime.now())
                    .isDeleted(false)
                    .changedBy(currentUserInfo.getId())
                    .build();
            courseDetailService.createCourseDetails(courseDetail);
        }
        return courseRepository.save(courseById);
    }

    @Transactional
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public void softDeleteCourse(String id) {
        Course course = getCourseById(id);
        course.setDeleted(true);
        courseRepository.save(course);
    }


    @Transactional
    public List<Course> getTopThreeMostRegisteredCourses() {
        return courseRepository.findTop3ByOrderByRegisteredUsersDesc();
    }

    @Transactional
    public List<Course> getCoursePurchasedByUser() {
        List<Course> courses = new ArrayList<>();
        List<String> courseIds = new ArrayList<>();
        var currentUser = userService.getMyInfo();
        if (currentUser != null) {
            List<Order> orders = orderRepository.findOrderByUserId(currentUser.getId());
            for (Order order : orders) {
                List<OrderDetail> orderDetails = order.getOrderDetails().stream().toList();

                for (OrderDetail orderDetail : orderDetails) {
                    String courseId = orderDetail.getCourseId();
                    Course course = courseRepository.findById(courseId).orElse(null);
                    if (course != null) {
                        courseIds.add(course.getId());
                    } else {
                        System.err.println("Course with id " + courseId + " not found.");
                    }
                }
            }
        } else {
            System.err.println("Current user not found.");
        }
        return courseRepository.findAllById(courseIds);
    }

    public List<Course> searchCoursesByName(String courseName) {
        return courseRepository.findByCourseNameContainingIgnoreCase(courseName);
    }
}
