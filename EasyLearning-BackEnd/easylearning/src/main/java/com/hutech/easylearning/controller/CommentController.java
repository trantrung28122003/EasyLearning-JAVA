package com.hutech.easylearning.controller;

import com.hutech.easylearning.dto.reponse.CommentResponse;
import com.hutech.easylearning.dto.reponse.FeedbackResponse;
import com.hutech.easylearning.dto.reponse.ReplyResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.CommentRequest;
import com.hutech.easylearning.dto.request.ReplyRequest;
import com.hutech.easylearning.entity.Comment;
import com.hutech.easylearning.repository.CommentRepository;
import com.hutech.easylearning.service.CommentService;
import com.hutech.easylearning.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/commentsByTrainingPart/{trainingPartId}")
    public ApiResponse<List<CommentResponse>> getComments(@PathVariable String trainingPartId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getCommentsByTrainingPartId(trainingPartId))
                .build();
    }


    @MessageMapping("/comment")
    @SendTo("/topic/comments")
    public CommentResponse handleComment(@RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentService.addComment(request);
        return commentResponse; // Lưu và gửi lại
    }


    @MessageMapping("/reply")
    @SendTo("/topic/replies")
    public ReplyResponse handleReply(@RequestBody ReplyRequest request) {
        ReplyResponse replyResponse = commentService.addReplyToComment(request);
        var notificationResponse = notificationService.addNotificationByComment(request.getCommentId());
        simpMessagingTemplate.convertAndSend("/topic/notifications", notificationResponse);
        return replyResponse; // Lưu và gửi lại phản hồi
    }
}
