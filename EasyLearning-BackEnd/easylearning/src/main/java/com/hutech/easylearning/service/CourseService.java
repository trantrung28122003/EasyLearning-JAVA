package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.*;
import com.hutech.easylearning.dto.request.CourseCreationRequest;
import com.hutech.easylearning.dto.request.CourseUpdateRequest;
import com.hutech.easylearning.entity.*;
import com.hutech.easylearning.enums.CourseType;
import com.hutech.easylearning.exception.AppException;
import com.hutech.easylearning.exception.ErrorCode;
import com.hutech.easylearning.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseService {
    final CourseRepository courseRepository;
    final UserService userService;
    final CategoryService categoryService;
    final CourseDetailService courseDetailService;
    final TrainerDetailService trainerDetailService;
    final OrderRepository orderRepository;
    final UploaderService uploaderService;
    final TrainingPartService trainingPartService;
    final ShoppingCartItemService shoppingCartItemService;
    final CourseEventRepository courseEventRepository;
    final TrainingPartRepository trainingPartRepository;
    final FeedbackRepository feedbackRepository;
    final UserRepository userRepository;
    final ShoppingCartRepository shoppingCartRepository;
    final ShoppingCartItemRepository shoppingCartItemRepository;
    final UserTrainingProgressService userTrainingProgressService;
    final CourseEventService courseEventService;
    final DiscountService discountService;
    final CourseDiscountRepository courseDiscountRepository;
    final UserFavoriteRepository userFavoriteRepository;

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


//    @Transactional
//    public List<MostRegisteredCoursesResponse> getTopThreeMostRegisteredCourses() {
//        List<Course> courses = courseRepository.findTop3ByOrderByRegisteredUsersDesc();
//        List<MostRegisteredCoursesResponse> mostRegisteredCoursesResponseList = new ArrayList<>();
//        for(var course : courses)
//        {
//            MostRegisteredCoursesResponse mostRegisteredCoursesResponse = new MostRegisteredCoursesResponse().builder()
//                    .courseName(course.getCourseName())
//                    .courseImage(course.getImageUrl())
//                    .averageRating(cour)
//
//                    .build();
//        }
//        return courseRepository.findTop3ByOrderByRegisteredUsersDesc();
//    }

@Transactional
    public List<Course> getTopFourMostRegisteredCourses() {
        return courseRepository.findTop4ByOrderByRegisteredUsersDesc();
    }

    @Transactional
    public List<Course> getCoursesFree() {
        return courseRepository.findTop4ByOrderByRegisteredUsersDesc();
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


                    var trainingPartByCourse = trainingPartRepository.findTrainingPartByCourseId(courseId);
                    int totalTrainingPartByCourse = trainingPartByCourse.size();
                    List<CourseEventResponse> courseEventResponses = courseEventService.getCourseEventsByCourse(course.getId());
                    Collections.sort(courseEventResponses, Comparator.comparing(CourseEventResponse::getStartTime));
                    var completePartsByCourse = userTrainingProgressService.getCompletedTrainingPartsOnCourses(courseId);



                    PurchasedCourseResponse purchasedCourseResponse = PurchasedCourseResponse.builder()
                            .courseId(course.getId())
                            .courseName(course.getCourseName())
                            .courseImage(course.getImageUrl())
                            .instructor(course.getInstructor())
                            .courseType(course.getCourseType())
                            .startDate(course.getStartDate())
                            .endDate(course.getEndDate())
                            .totalTrainingPartByCourse(totalTrainingPartByCourse)
                            .completedPartsByCourse(completePartsByCourse)
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

        var avatarInstructor = "https://easylearning.blob.core.windows.net/images-videos/user1.jpgea0c0be2-11c0-4948-b908-fcfa615b7835";
        Course courseById = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        var trainingPartByCourse = trainingPartRepository.findTrainingPartByCourseId(courseId);

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
                            .location(courseEvent.getLocation())
                            .build();
                    if (courseEventResponses.stream()
                            .noneMatch(existing -> existing.getId().equals(courseEventResponse.getId()))){
                        var totalTrainingPartByCourseEvent = trainingPartRepository.findTrainingPartByCourseEventId(courseEventResponse.getId()).size();
                        var completedPartsByCourseEvent = userTrainingProgressService.getCompletedTrainingPartsOnCourseEvent(courseEventResponse.getId());
                        courseEventResponse.setCompletedPartsByCourseEvent(completedPartsByCourseEvent);
                        courseEventResponse.setTotalPartsByCourseEvent(totalTrainingPartByCourseEvent);


                        courseEventResponses.add(courseEventResponse);
                    }
                }
            }

        }
        Collections.sort(courseEventResponses, Comparator.comparing(CourseEventResponse::getStartTime));
        var completedPartsByCourse = userTrainingProgressService.getCompletedTrainingPartsOnCourses(courseById.getId());
        ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                .courseId(courseById.getId())
                .courseName(courseById.getCourseName())
                .avatarInstructor(avatarInstructor)

                .nameInstructor(courseById.getInstructor())
                .CourseEventResponse(courseEventResponses)
                .build();
        return scheduleResponse;
    }

    public String calculateTotalLearningTime(List<CourseEventResponse> courseEventResponses) {
        long totalTimeInSeconds = 0;

        for (var courseEventResponse : courseEventResponses) {

            LocalDateTime startTime = courseEventResponse.getStartTime();
            LocalDateTime endTime = courseEventResponse.getEndTime();


            long eventDurationInSeconds = Duration.between(startTime, endTime).getSeconds();
            totalTimeInSeconds += eventDurationInSeconds;
        }

        long totalHours = totalTimeInSeconds / 3600;
        long totalMinutes = (totalTimeInSeconds % 3600) / 60;


        return totalHours + ":" + totalMinutes;
    }
    public DetailCourseResponse getDetailCourse(String courseId)
    {
        var courseById = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        var trainingPartByCourse = trainingPartRepository.findTrainingPartByCourseId(courseById.getId());
        List<FeedbackInfoResponse> feedbackInfos = new ArrayList<>();
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
                        courseEventResponse.setTotalPartsByCourseEvent(totalTrainingPartByCourseEvent);
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

        for (var feedback : feedbacksByCourseId) {
            var userWithFeedback = userRepository.findById(feedback.getFeedbackUserId()).orElseThrow(() -> new RuntimeException("User not found with id: " + feedback.getFeedbackUserId()));
            var typeUser = "Khách hàng";
            FeedbackInfoResponse feedbackResponse = FeedbackInfoResponse.builder()
                    .feedbackId(feedback.getId())
                    .courseId(feedback.getCourseId())
                    .userId(feedback.getFeedbackUserId())
                    .content(feedback.getFeedbackContent())
                    .fullNameUser(userWithFeedback.getFullName())
                    .typeUser(typeUser)
                    .avatar(userWithFeedback.getImageUrl())
                    .dateChange(feedback.getDateChange())
                    .build();
            feedbackInfos.add(feedbackResponse);
        }



        String totalLearningTime = calculateTotalLearningTime(courseEventResponses);

        var coursePriceDiscount = discountService.applyCourseDiscount(courseById.getId(), courseById.getCoursePrice());
        if (courseById.getCoursePrice().compareTo(coursePriceDiscount) == 0) { coursePriceDiscount = null; }

        DetailCourseResponse detailCourseResponse = DetailCourseResponse.builder()
                .courseId(courseById.getId())
                .courseName(courseById.getCourseName())
                .coursePrice(courseById.getCoursePrice())
                .coursePriceDiscount(coursePriceDiscount)
                .courseImage(courseById.getImageUrl())
                .nameInstructor(courseById.getInstructor())
                .courseEventResponses(courseEventResponses)
                .totalFeedback(totalFeedback)
                .averageRating(averageRating)
                .feedFeedbackInfoResponses(feedbackInfos)
                .totalLearningTime(totalLearningTime)
                .learningOutcomes(courseById.getLearningOutcomes().stream().toList())
                .nextAvailableDate(courseById.getNextAvailableDate())
                .build();

        return detailCourseResponse;

    }
    public boolean isCourseInCart(String courseId) {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        var currentUser = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var shoppingCart = shoppingCartRepository.findShoppingCartByUserId(currentUser.getId());
        if (shoppingCart == null) {
            return false;
        }

        var shoppingCartItems =  shoppingCartItemRepository.findShoppingCartItemByShoppingCartId(shoppingCart.getId())
                .stream()
                .filter(shoppingCartItem -> !shoppingCartItem.isDeleted())
                .collect(Collectors.toList());

        for (var shoppingCartItem : shoppingCartItems) {
            if (shoppingCartItem.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
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
                }
            }
        }
        return false;
    }
    public boolean isFeedbackByUser(String courseId) {
        var currentUser = userService.getMyInfo();

        if (currentUser != null) {
            List<Feedback> feedbacksByCourse = feedbackRepository.findByCourseId(courseId);
            for (Feedback feedback : feedbacksByCourse) {
                if(feedback.getFeedbackUserId().equals(currentUser.getId()))
                {
                    return true;
                }
            }

        }
        return false;
    }

    @Transactional
    public CourseStatusResponse getCourseStatus(String courseId) {
        var currentUser = userService.getMyInfo();
        boolean isPurchased = false;
        boolean isLearning = false;
        boolean isInCart = false;
        boolean isFeedback = false;
        boolean isFavorited = false;
        boolean isCourseFull = false;
        boolean isRegistrationDateExpired = false;
        boolean isFree = false;
        if(isCourseInCart(courseId)){
            isInCart = true;
        }
        if(isCourseInSchedule(courseId)){
            isLearning = true;
            isPurchased = true;
        }

        if(isFeedbackByUser(courseId)){
            isFeedback = true;
        }
        if(isCourseOfflineFull(courseId))
        {
            isCourseFull = true;
        }
        if(isRegistrationDateExpired(courseId))
        {
            isRegistrationDateExpired = true;
        }
        if(isCourseFree(courseId))
        {
            isFree = true;
        }
        if(userFavoriteRepository.findByUserIdAndCourseId(currentUser.getId(), courseId).isPresent()){
            isFavorited = true;
        }
        CourseStatusResponse courseStatusResponse = new CourseStatusResponse().builder()
                .isLearning(isLearning)
                .isInCart(isInCart)
                .isPurchased(isPurchased)
                .isFeedback(isFeedback)
                .isFavorited(isFavorited)
                .isCourseFull(isCourseFull)
                .isRegistrationDateExpired(isRegistrationDateExpired)
                .isFree(isFree)
                .build();
        return courseStatusResponse;
    }

    public List<Course> searchByQuery(String query) {
        if (query != null && !query.trim().isEmpty()) {
            return courseRepository.findByCourseNameContainingIgnoreCase(query);
        } else {
            return courseRepository.findAll();
        }
    }
    public List<Course> searchCourses(String query, String sortBy, String courseType, Integer rating) {
        List<Course> courses  = searchByQuery(query);

        if (courseType != null && !courseType.isEmpty()) {
            courses = courses.stream()
                    .filter(course -> course.getCourseType().name().equalsIgnoreCase(courseType))
                    .collect(Collectors.toList());
        }
        if (rating != null) {
//            courses = courses.stream()
//                    .filter(course -> course.getRating() != null && course.getRating() >= rating)
//                    .collect(Collectors.toList());
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "az":
                    courses.sort(Comparator.comparing(Course::getCourseName));
                    break;
                case "za":
                    courses.sort(Comparator.comparing(Course::getCourseName).reversed());
                    break;
                case "priceasc":
                    courses.sort(Comparator.comparing(Course::getCoursePrice));
                    break;
                case "pricedesc":
                    courses.sort(Comparator.comparing(Course::getCoursePrice).reversed());
                    break;
                default:
                    break;
            }
        }

        return courses;
    }


    public List<Course> getAllCourseWithDiscount() {
        return courseDiscountRepository.findDistinctCoursesWithDiscount();
    }
    public List<Course> getCourseWithFree() {
        return courseRepository.findCoursesByIsFree(true);
    }


    public boolean isCourseOfflineFull(String courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return false;
        }
        if(course.getCourseType() == CourseType.ONLINE)
        {
            return false;
        }
        return course.getRegisteredUsers() >= course.getMaxAttendees();
    }

    public boolean isCourseFree(String courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return false;
        }
        if(course.isFree())
        {
            return true;
        }
        return false;
    }

    public boolean isRegistrationDateExpired(String courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return false;
        }
        if(course.getCourseType() == CourseType.ONLINE)
        {
            return false;
        }
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(course.getRegistrationDeadline());
    }

}
