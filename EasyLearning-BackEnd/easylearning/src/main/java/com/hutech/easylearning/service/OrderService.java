package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.OrderDetailResponse;
import com.hutech.easylearning.dto.reponse.PurchaseHistoryResponse;
import com.hutech.easylearning.dto.request.OrderRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.Order;
import com.hutech.easylearning.entity.OrderDetail;
import com.hutech.easylearning.entity.UserTrainingProgress;
import com.hutech.easylearning.repository.CourseRepository;
import com.hutech.easylearning.repository.OrderRepository;
import com.hutech.easylearning.repository.TrainingPartRepository;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderService {

    final OrderRepository orderRepository;
    final ShoppingCartItemService shoppingCartItemService;
    final UserService userService;
    final OrderDetailService orderDetailService;
    final EmailService emailService;
    final TrainingPartService trainingPartService;
    final UserTrainingProgressService userTrainingProgressService;
    final CourseRepository courseRepository;
    final NotificationService notificationService;
    final SimpMessagingTemplate simpMessagingTemplate;
    
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Transactional
    public boolean processPaymentAndCreateOrder(OrderRequest request) {
        var shoppingCartItems = shoppingCartItemService.getShoppingCartItemsByCurrentUser();
        var currentUser = userService.getMyInfo();
        BigDecimal orderAmount = new BigDecimal(request.getAmount());
        Order order = Order.builder()
                .orderTotalPrice(orderAmount)
                .orderNotes(request.getNote())
                .orderPaymentMethod(request.getPaymentMethod())
                .orderQuantity(shoppingCartItems.size())
                .dateCreate(LocalDateTime.now())
                .userId(currentUser.getId())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUser.getId())
                .isDeleted(false)
                .isFree(false)
                .build();
        Order saveOrder = orderRepository.save(order);

        for (var itemShoppingCart : shoppingCartItems) {
            var orderDetail = OrderDetail.builder()
                    .orderId(order.getId())
                    .courseId(itemShoppingCart.getCourseId())
                    .orderDetailPrice(itemShoppingCart.getCartItemPrice())
                    .orderDetailDiscount(itemShoppingCart.getCartItemPriceDiscount())
                    .dateCreate(LocalDateTime.now())
                    .dateChange(LocalDateTime.now())
                    .changedBy(currentUser.getId())
                    .isDeleted(false)
                    .build();
            orderDetailService.createOrderDetail(orderDetail);

            //var trainingPartsByCourse = trainingPartService.getTrainingPartsByCourseId(itemShoppingCart.getCourseId());
            for (var itemTrainingPart : itemShoppingCart.getCourse().getTrainingParts()) {
                UserTrainingProgress userTrainingProgress = new UserTrainingProgress().builder()
                        .userId(currentUser.getId())
                        .trainingPartId(itemTrainingPart.getId())
                        .isCompleted(false)
                        .changedBy(currentUser.getId())
                        .dateChange(LocalDateTime.now())
                        .dateCreate(LocalDateTime.now())
                        .build();
                userTrainingProgressService.createUserTrainingProgress(userTrainingProgress);
            }

            var course = courseRepository.findById(itemShoppingCart.getCourseId()).orElseThrow();
            var currentRegistered = course.getRegisteredUsers();
            course.setRegisteredUsers(currentRegistered + 1);
            var notificationResponse = notificationService.addNotificationByPurchaseCourse(itemShoppingCart.getCourseId());

            String destination = "/notifications";
            try {
                simpMessagingTemplate.convertAndSendToUser(currentUser.getId(), destination, notificationResponse);
                System.out.println("Gửi tin nhắn tới: /user/" + currentUser.getId() + destination);
            } catch (Exception e) {
                System.err.println("Lỗi khi gửi tin nhắn: " + e.getMessage());
                e.printStackTrace();
            }
        }

        var toEmail = currentUser.getEmail();
        var subject = "Thanh toán thành công trên trang eLearning";
        var customerName = currentUser.getFullName();
        var totalCourses = String.valueOf(shoppingCartItems.size());
        var authorizationCode = order.getId();
        LocalDateTime orderDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String orderDateString = orderDate.format(formatter);

        List<String> courseByNames = new ArrayList<>();

        for (var itemShoppingCart : shoppingCartItems) {
            courseByNames.add(itemShoppingCart.getCourse().getCourseName());
            shoppingCartItemService.deleteShoppingCartItem(itemShoppingCart.getId());
        }

        if(saveOrder !=null) {
            emailService.sendEmailPaymentAsync(toEmail, subject, customerName, orderAmount, totalCourses, authorizationCode, orderDateString, courseByNames);
        }
        return saveOrder != null;
    }
    @Transactional
    public boolean addFreeCourseOrder(String courseId) {
        var currentUser = userService.getMyInfo();
        var course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        Order order = Order.builder()
                .orderTotalPrice(BigDecimal.ZERO)
                .orderNotes("Miễn phí")
                .orderPaymentMethod("Miễn phí")
                .orderQuantity(1)
                .dateCreate(LocalDateTime.now())
                .userId(currentUser.getId())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUser.getId())
                .isDeleted(false)
                .isFree(true)
                .build();
        Order savedOrder = orderRepository.save(order);

        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(savedOrder.getId())
                .courseId(courseId)
                .orderDetailPrice(BigDecimal.ZERO)
                .orderDetailDiscount(BigDecimal.ZERO)
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUser.getId())
                .isDeleted(false)
                .build();
        orderDetailService.createOrderDetail(orderDetail);

        for (var trainingPart : course.getTrainingParts()) {
            UserTrainingProgress userTrainingProgress = new UserTrainingProgress().builder()
                    .userId(currentUser.getId())
                    .trainingPartId(trainingPart.getId())
                    .isCompleted(false)
                    .changedBy(currentUser.getId())
                    .dateChange(LocalDateTime.now())
                    .dateCreate(LocalDateTime.now())
                    .build();
            userTrainingProgressService.createUserTrainingProgress(userTrainingProgress);
        }
        var notificationResponse = notificationService.addNotificationByPurchaseCourse(courseId);
        String destination = "/notifications";
        try {
            simpMessagingTemplate.convertAndSendToUser(currentUser.getId(), destination, notificationResponse);
            System.out.println("Gửi thông báo tới: /user/" + currentUser.getId() + destination);
        } catch (Exception e) {
            System.err.println("Lỗi khi gửi thông báo: " + e.getMessage());
            e.printStackTrace();
        }
        return savedOrder != null;
    }



    public PurchaseHistoryResponse getPurchaseHistory() {
        var ordersByUser = orderRepository.findOrderByUserId(userService.getMyInfo().getId());
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (var order : ordersByUser) {
            orderDetails.addAll(order.getOrderDetails().stream().toList());
        }
        for (var orderDetail : orderDetails) {

            OrderDetailResponse orderDetailResponse = new OrderDetailResponse().builder()
                    .price(orderDetail.getOrderDetailPrice())
                    .priceDiscount(orderDetail.getOrderDetailDiscount())
                    .courseName(orderDetail.getCourse().getCourseName())
                    .isFree(orderDetail.getCourse().isFree())
                    .orderDate(orderDetail.getDateCreate())
                    .build();
            orderDetailResponses.add(orderDetailResponse);
        }
        BigDecimal initialAmount = orderDetails.stream()
                .map(OrderDetail::getOrderDetailPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountedAmount = orderDetails.stream()
                .map(OrderDetail::getOrderDetailDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime dateOfFirstPurchase = orderDetails.stream()
                .map(OrderDetail::getDateCreate)
                .min(LocalDateTime::compareTo)
                .orElse(null);
        LocalDateTime dateOfLatestPurchase = orderDetails.stream()
                .map(OrderDetail::getDateCreate)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        PurchaseHistoryResponse purchaseHistoryResponse = new PurchaseHistoryResponse()
                .builder()
                .initialAmount(initialAmount)
                .discountedAmount(discountedAmount)
                .dateOfFirstPurchase(dateOfFirstPurchase)
                .dateOfLatestPurchase(dateOfLatestPurchase)
                .orderDetailResponseList(orderDetailResponses)
                .build();
        return purchaseHistoryResponse;
    }


    @Transactional
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
    @Transactional
    public void softDeleteOrder(String id) {
        Order order = getOrderById(id);
        order.setDeleted(true);
        orderRepository.save(order);
    }
}
