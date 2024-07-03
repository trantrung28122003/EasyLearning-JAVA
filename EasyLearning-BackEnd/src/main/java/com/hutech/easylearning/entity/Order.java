package com.hutech.easylearning.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "order_total_price", precision = 10, scale = 3)
    BigDecimal orderTotalPrice;

    @Column(name = "order_payment_method")
    String orderPaymentMethod;

    @Column(name = "order_notes")
    String orderNotes;

    @Column(name = "order_quantity")
    String orderQuantity;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @Column(name = "order_user")
    String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_user", referencedColumnName = "id", insertable = false, updatable = false)
    User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    Set<OrderDetail> orderDetails;
}

