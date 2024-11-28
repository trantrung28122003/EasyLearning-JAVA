package com.hutech.easylearning.controller;

import com.hutech.easylearning.entity.Comment;
import com.hutech.easylearning.entity.Reply;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CommentWebSocketController {

//    private final SimpMessagingTemplate messagingTemplate;
//
//    public CommentWebSocketController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @MessageMapping("/comment")
//    @SendTo("/topic/comments")
//    public Comment sendComment(Comment comment) {
//        // Gửi bình luận đến tất cả client
//        messagingTemplate.convertAndSend("/topic/comments", comment);
//        return comment; // Trả lại comment để gửi cho tất cả client
//    }
//
//    @MessageMapping("/reply")
//    @SendTo("/topic/comments")
//    public Reply sendReply(Reply reply) {
//        messagingTemplate.convertAndSend("/topic/comments", reply);
//        return reply;
//    }
}

