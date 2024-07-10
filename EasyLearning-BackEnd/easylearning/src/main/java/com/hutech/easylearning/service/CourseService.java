package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.CourseEventResponse;
import com.hutech.easylearning.dto.reponse.DetailCourseResponse;
import com.hutech.easylearning.dto.reponse.PurchasedCourseResponse;
import com.hutech.easylearning.dto.reponse.ScheduleResponse;
import com.hutech.easylearning.dto.request.CourseCreationRequest;
import com.hutech.easylearning.dto.request.CourseUpdateRequest;
import com.hutech.easylearning.entity.*;
import com.hutech.easylearning.enums.CourseType;
import com.hutech.easylearning.exception.AppException;
import com.hutech.easylearning.exception.ErrorCode;
import com.hutech.easylearning.repository.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


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
    private UploaderService uploaderService;

    @Autowired
    private TrainingPartService trainingPartService;

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

    @Autowired
    private CourseEventRepository courseEventRepository;

    @Autowired
    private TrainingPartRepository trainingPartRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Course createCourse(CourseCreationRequest request, MultipartFile file) {
        String imageLink = "https://easylearning.blob.core.windows.net/images-videos/default_course.png";
        if(file != null) {
            imageLink = uploaderService.uploadFile(file);
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
            courseDetailService.createCourseDetail(courseDetail);
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
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Course updateCourse(String courseId, CourseUpdateRequest request, MultipartFile file) {
        var courseById = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        var currentUserInfo  =  userService.getMyInfo();
        if (file != null) {
            String imageLink = uploaderService.uploadFile(file);
            courseById.setImageUrl(imageLink);
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
            courseDetailService.createCourseDetail(courseDetail);
        }
        return courseRepository.save(courseById);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void softDeleteCourse(String courseId) {
        courseDetailService.softDeleteCourseDetailsByCategoryId(courseId);
        trainingPartService.softDeleteTrainingPartAndCourseEventByCourseId(courseId);
        shoppingCartItemService.softDeleteShoppingCartItemByCourseId(courseId);
        Course course = getCourseById(courseId);
        course.setDeleted(true);
        course.setDateChange(LocalDateTime.now());
        courseRepository.save(course);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void restoreCourse(String courseId) {
        courseDetailService.restoreCourseDetailsByCategoryId(courseId);
        trainingPartService.restoreTrainingPartAndCourseEventByCourseId(courseId);
        shoppingCartItemService.restoreShoppingCartItemByCourseId(courseId);
        Course course = getCourseById(courseId);
        course.setDeleted(false);
        course.setDateChange(LocalDateTime.now());
        courseRepository.save(course);
    }


    @Transactional
    public List<Course> getTopThreeMostRegisteredCourses() {
        return courseRepository.findTop3ByOrderByRegisteredUsersDesc();
    }

    @Transactional
    public List<PurchasedCourseResponse> getCoursePurchasedByUser() {

        List<PurchasedCourseResponse> purchasedCourseResponses = new ArrayList<>();
        var currentUser = userService.getMyInfo();
        if (currentUser != null) {
            List<Order> orders = orderRepository.findOrderByUserId(currentUser.getId());
            for (Order order : orders) {
                List<OrderDetail> orderDetails = order.getOrderDetails().stream().toList();
                for (OrderDetail orderDetail : orderDetails) {
                    String courseId = orderDetail.getCourseId();
                    Course course = courseRepository.findById(courseId).orElse(null);

                    var trainingPartByCourse = trainingPartService.getTrainingPartsByCourseId(course.getId());
                    int totalTrainingPartByCourse = trainingPartByCourse.size();
                    List<CourseEventResponse> courseEventResponses = new ArrayList<>();
                    for(var trainingPartId : trainingPartByCourse)
                    {
                        for(var courseEvent : courseEventRepository.findAll())
                        {
                            if(courseEvent.getId().equals(trainingPartId.getCourseEventId())) {
                                CourseEventResponse courseEventResponse = CourseEventResponse.builder()
                                        .id(courseEvent.getId())
                                        .courseEventName(courseEvent.getEventName())
                                        .startTime(courseEvent.getDateStart())
                                        .endTime(courseEvent.getDateEnd())
                                        .build();
                                if (courseEventResponses.stream()
                                        .noneMatch(existing -> existing.getId().equals(courseEventResponse.getId()))){
                                    var totalTrainingPartByCourseEvent = trainingPartRepository.findTrainingPartByCourseEventId(courseEventResponse.getId()).size();
                                    courseEventResponse.setTotalTrainingPartByCourseEvent(totalTrainingPartByCourseEvent);
                                    courseEventResponses.add(courseEventResponse);
                                }
                            }
                        }
                    }
                    Collections.sort(courseEventResponses, Comparator.comparing(CourseEventResponse::getStartTime));

                    PurchasedCourseResponse purchasedCourseResponse = PurchasedCourseResponse.builder()
                            .courseId(course.getId())
                            .courseName(course.getCourseName())
                            .courseImage(course.getImageUrl())
                            .totalTrainingPartByCourse(totalTrainingPartByCourse)
                            .courseEventResponses(courseEventResponses)
                            .build();
                    purchasedCourseResponses.add(purchasedCourseResponse);
                }
            }
        } else {
            System.err.println("Current user not found.");
        }
        return purchasedCourseResponses;
    }

    public List<Course> searchCoursesByName(String courseName) {
        return courseRepository.findByCourseNameContainingIgnoreCase(courseName);
    }

    public ScheduleResponse getPurchasedCoursesSchedule(String courseId)
    {
        var user = userService.getUserById("c6d703c2-7fab-4e0c-86f2-a5778ee8c60d");
        var avatarInstructor = user.getImageUrl();

        Course courseById = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        var trainingPartByCourse = trainingPartService.getTrainingPartsByCourseId(courseId);

        List<CourseEventResponse> courseEventResponses = new ArrayList<>();
        for(var trainingPartId : trainingPartByCourse)
        {
            for(var courseEvent : courseEventRepository.findAll())
            {
                if(courseEvent.getId().equals(trainingPartId.getCourseEventId())) {
                    CourseEventResponse courseEventResponse = CourseEventResponse.builder()
                            .id(courseEvent.getId())
                            .courseEventName(courseEvent.getEventName())
                            .startTime(courseEvent.getDateStart())
                            .endTime(courseEvent.getDateEnd())
                            .build();
                    if (courseEventResponses.stream()
                            .noneMatch(existing -> existing.getId().equals(courseEventResponse.getId()))){
                        var totalTrainingPartByCourseEvent = trainingPartRepository.findTrainingPartByCourseEventId(courseEventResponse.getId()).size();
                        courseEventResponse.setTotalTrainingPartByCourseEvent(totalTrainingPartByCourseEvent);
                        courseEventResponses.add(courseEventResponse);
                    }
                }
            }

        }
        Collections.sort(courseEventResponses, Comparator.comparing(CourseEventResponse::getStartTime));

        ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                .courseId(courseById.getId())
                .courseName(courseById.getCourseName())
                .avatarInstructor(avatarInstructor)
                .CourseEventResponse(courseEventResponses)
                .build();
        return scheduleResponse;
    }
    public DetailCourseResponse getDetailCourse(String courseId)
    {
        var courseById = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        var trainingPartByCourse = trainingPartService.getTrainingPartsByCourseId(courseById.getId());
        List<CourseEventResponse> courseEventResponses = new ArrayList<>();
        for(var trainingPartId : trainingPartByCourse)
        {
            for(var courseEvent : courseEventRepository.findAll())
            {
                if(courseEvent.getId().equals(trainingPartId.getCourseEventId())) {
                    CourseEventResponse courseEventResponse = CourseEventResponse.builder()
                            .id(courseEvent.getId())
                            .courseEventName(courseEvent.getEventName())
                            .startTime(courseEvent.getDateStart())
                            .endTime(courseEvent.getDateEnd())
                            .build();
                    if (courseEventResponses.stream()
                            .noneMatch(existing -> existing.getId().equals(courseEventResponse.getId()))){
                        var trainingPartByCourseEvent = trainingPartRepository.findTrainingPartByCourseEventId(courseEventResponse.getId()).stream()
                                .sorted(Comparator.comparing(TrainingPart::getStartTime))
                                .collect(Collectors.toList());;
                        var totalTrainingPartByCourseEvent = trainingPartByCourseEvent.size();
                        courseEventResponse.setTotalTrainingPartByCourseEvent(totalTrainingPartByCourseEvent);
                        courseEventResponse.setTrainingParts(trainingPartByCourseEvent);
                        courseEventResponses.add(courseEventResponse);
                    }
                }
            }
        }
        Collections.sort(courseEventResponses, Comparator.comparing(CourseEventResponse::getStartTime));

        int totalFeedback = 0;
        int averageRating = 0;
        var feedbacksByCourseId = feedbackRepository.findByCourseId(courseById.getId());
        totalFeedback = feedbacksByCourseId.size();
        if (totalFeedback > 0) {
            int totalRating = 0;
            for (Feedback feedback : feedbacksByCourseId) {
                totalRating += feedback.getFeedbackRating();
            }
            averageRating = totalRating / totalFeedback;
        }else {
        }

        DetailCourseResponse detailCourseResponse = DetailCourseResponse.builder()
                .courseId(courseById.getId())
                .courseName(courseById.getCourseName())
                .coursePrice(courseById.getCoursePrice())
                .courseImage(courseById.getImageUrl())
                .nameInstructor(courseById.getInstructor())
                .courseEventResponses(courseEventResponses)
                .totalFeedback(totalFeedback)
                .averageRating(averageRating)
                .build();

        return detailCourseResponse;

    }

    public boolean isCourseInSchedule(String courseId) {
        var currentUser = userService.getMyInfo();
        if (currentUser != null) {
            List<Order> orders = orderRepository.findOrderByUserId(currentUser.getId());
            for (Order order : orders) {
                List<OrderDetail> orderDetails = order.getOrderDetails().stream().toList();
                for (OrderDetail orderDetail : orderDetails) {
                    if(orderDetail.getCourseId().equals(courseId))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        return false;
    }

}
