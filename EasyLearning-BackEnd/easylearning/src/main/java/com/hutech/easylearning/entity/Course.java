package com.hutech.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hutech.easylearning.enums.CourseType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity(name = "Course")
@Table(name = "Course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "course_name")
    String courseName;

    @Column(name = "course_description")
    String courseDescription;

    @Column(name = "course_price",precision = 10, scale = 3)
    BigDecimal coursePrice;

    @Column(name = "course_requirements")
    String requirements;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type")
    CourseType courseType;

    @Column(name = "course_content")
    String courseContent;

    @Column(name = "courses_imageUrl")
    String imageUrl;

    @Column(name = "course_instructor")
    String instructor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "course_startDate")
    LocalDateTime startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "course_endDate")
    LocalDateTime endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "course_registrationDeadline")
    LocalDateTime registrationDeadline;

    @Column(name = "course_maxAttendees")
    int maxAttendees;

    @Column(name = "course_registeredUsers")
    int registeredUsers;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<CourseDetail> coursesDetails;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<TrainerDetail> trainerDetails;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<TrainingPart> trainingParts;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<ShoppingCartItem> shoppingCartItems;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<AddOn> addOns;
}
