package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.FeedbackInfoResponse;
import com.hutech.easylearning.dto.reponse.FeedbackResponse;
import com.hutech.easylearning.dto.request.FeedbackCreationRequest;
import com.hutech.easylearning.dto.request.FeedbackUpdateRequest;
import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.exception.AppException;
import com.hutech.easylearning.exception.ErrorCode;
import com.hutech.easylearning.repository.FeedbackRepository;
import com.hutech.easylearning.repository.OrderDetailRepository;
import com.hutech.easylearning.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackService {

    final FeedbackRepository feedbackRepository;
    final OrderDetailRepository orderDetailRepository;
    final UserRepository userRepository;

    @Autowired
    UserService userService;

    @Transactional(readOnly = true)
    public Feedback getFeedbackById(String id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
    }


    @Transactional(readOnly = true)
    public FeedbackResponse getFeedbacksByCourse(String courseId) {
        List<FeedbackInfoResponse> feedbackInfos = new ArrayList<>();
        var feedbacks = feedbackRepository.findByCourseId(courseId);
        var currentUser = userService.getMyInfo();
        var hasBought = orderDetailRepository.existsByOrderUserIdAndCourseId(currentUser.getId(), courseId);
        for (var feedback : feedbacks) {
            var typeUser = feedback.getFeedbackUserId().equals(currentUser.getId()) ? "Bạn" : "Khách hàng";
            FeedbackInfoResponse feedbackResponse = FeedbackInfoResponse.builder()
                    .feedbackId(feedback.getId())
                    .courseId(feedback.getCourseId())
                    .userId(feedback.getFeedbackUserId())
                    .content(feedback.getFeedbackContent())
                    .fullNameUser(currentUser.getFullName())
                    .typeUser(typeUser)
                    .avatar(currentUser.getImageUrl())
                    .dateChange(feedback.getDateChange())
                    .build();
            feedbackInfos.add(feedbackResponse);
            if(feedback.getFeedbackUserId().equals(currentUser.getId())) {
                hasBought = false;
            }
        }

        FeedbackResponse feedbackResponse = FeedbackResponse.builder()
                .feedbacks(feedbackInfos)
                .setHasGivenFeedback(hasBought)
                .build();
        return feedbackResponse;
    }

    @Transactional(readOnly = true)
    public FeedbackResponse getFeedbacksByCourseWithoutUser(String courseId) {
        List<FeedbackInfoResponse> feedbackInfos = new ArrayList<>();

        var feedbacks = feedbackRepository.findByCourseId(courseId);
        String fullName = "Khách hàng";
        String avatar = "";


        for (var feedback : feedbacks) {
            var user = userRepository.findById(feedback.getFeedbackUserId()).orElse(null);
            if (user != null) {
                fullName = user.getFullName();
                avatar = user.getImageUrl();
            }
            FeedbackInfoResponse feedbackResponse = FeedbackInfoResponse.builder()
                    .feedbackId(feedback.getId())
                    .courseId(feedback.getCourseId())
                    .userId(feedback.getFeedbackUserId())
                    .content(feedback.getFeedbackContent())
                    .fullNameUser(fullName)
                    .typeUser("Khách hàng")
                    .avatar(avatar)
                    .feedbackRating(feedback.getFeedbackRating())
                    .dateChange(feedback.getDateChange())
                    .build();

            feedbackInfos.add(feedbackResponse);
        }
        FeedbackResponse feedbackResponse = FeedbackResponse.builder()
                .feedbacks(feedbackInfos)
                .setHasGivenFeedback(false)  // Vì người dùng chưa đăng nhập, không có phản hồi
                .build();

        return feedbackResponse;
    }


    @Transactional
    public List<FeedbackInfoResponse>  getFeedbacksWithFiveRating() {
        var feedbacks = feedbackRepository.findByFeedbackRating(5);
        List<FeedbackInfoResponse> feedbackInfos = new ArrayList<>();
        for (var feedback : feedbacks) {
            var user = userRepository.findById(feedback.getFeedbackUserId()).orElse(null);
            if (user != null) {
                FeedbackInfoResponse feedbackInfoResponse = FeedbackInfoResponse.builder()
                        .content(feedback.getFeedbackContent())
                        .avatar(user.getImageUrl())
                        .fullNameUser(user.getFullName())
                        .feedbackRating(feedback.getFeedbackRating())
                        .build();
                feedbackInfos.add(feedbackInfoResponse);
            }
        }
        return feedbackInfos;
    }

    @Transactional
    public Feedback createFeedback(FeedbackCreationRequest request) {
        var currentUser = userService.getMyInfo();
        Feedback feedback = Feedback.builder()
                .feedbackContent(request.getFeedbackContent())
                .feedbackRating(request.getFeedbackRating())
                .feedbackUserId(currentUser.getId())
                .courseId(request.getCourseId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUser.getId())
                .isDeleted(false)
                .build();
        return feedbackRepository.save(feedback);
    }

    @Transactional

    public Feedback updateFeedback(String feedbackId, FeedbackUpdateRequest request) {
        var feedback = getFeedbackById(feedbackId);
        var currentUser = userService.getMyInfo();

        if (!feedback.getFeedbackUserId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UPDATE_OWN_FEEDBACK_ONLY);
        }
        feedback.setFeedbackContent(request.getFeedbackContent());
        feedback.setFeedbackRating(request.getFeedbackRating());
        feedback.setDateChange(LocalDateTime.now());
        feedback.setChangedBy(currentUser.getId());
        return feedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }
    @Transactional
    public void softDeleteFeedback(String id) {
        Feedback feedback = getFeedbackById(id);
        feedback.setDeleted(true);
        feedbackRepository.save(feedback);
    }
}

