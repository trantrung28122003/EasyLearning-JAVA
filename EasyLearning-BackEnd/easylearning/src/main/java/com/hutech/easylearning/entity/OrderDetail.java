package com.hutech.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "order_id")
    String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    Order order;

    @Column(name = "Course_Id")
    String courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Course_Id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    Course course;

    @Column(name = "dateCreate")
    LocalDateTime dateCreate;

    @Column(name = "dateChange")
    LocalDateTime dateChange;

    @Column(name = "changedBy")
    String changedBy;

    @Column(name = "isDeleted")
    boolean isDeleted;
}

