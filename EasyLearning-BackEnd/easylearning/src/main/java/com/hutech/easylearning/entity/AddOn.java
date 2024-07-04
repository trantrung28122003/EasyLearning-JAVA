package com.hutech.easylearning.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "add_on")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AddOn {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "add_on_name")
    String addOnName;

    @Column(name = "link_download")
    String linkDownload;

    @Column(name = "add_on_price", columnDefinition = "decimal(10,3)")
    double coursesPrice;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @Column(name = "courses_id")
    String courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id", referencedColumnName = "id", insertable = false, updatable = false)
    Course course;
}

