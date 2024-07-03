package com.hutech.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "course_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "course_detail_course_id")
    String courseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_detail_course_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    Course course;

    @Column(name = "course_detail_category_id")
    String categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_detail_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    Category category;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;
}


