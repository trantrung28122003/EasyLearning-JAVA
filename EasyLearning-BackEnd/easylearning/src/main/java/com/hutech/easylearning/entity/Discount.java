package com.hutech.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hutech.easylearning.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "discount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name ="discount_code")
    String discountCode;

    @Column(name ="discount_name")
    String discountName;

    @Enumerated(EnumType.STRING)
    @Column(name ="discount_type")
    DiscountType discountType;

    @Column(name ="start_date")
    LocalDateTime startDate;

    @Column(name ="end_date")
    LocalDateTime endDate;

    @Column(name ="active")
    Boolean active;

    @Column(name ="value")
    BigDecimal value;

    @Column(name = "usage_limit")
    Integer usageLimit;

    @Column(name = "usage_count")
    Integer usageCount;

    @Column(name = "description")
    String description;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<CourseDiscount> courseDiscounts;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<UserDiscount> userDiscounts;
}
