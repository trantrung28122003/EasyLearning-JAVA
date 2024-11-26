package com.hutech.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "shopping_cart_item_name")
    String cartItemName;

    @Column(name = "shopping_cart_item_quantity")
    Integer quantity;

    @Column(name = "shopping_cart_item_imageUrl")
    String imageUrl;

    @Column(name = "shopping_cart_item_price", precision = 10, scale = 3)
    BigDecimal cartItemPrice;

    @Column(name = "shopping_cart_item_price_discount", precision = 10, scale = 3)
    BigDecimal cartItemPriceDiscount;

    @Column(name ="shopping_cart_id")
    String shoppingCartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    ShoppingCart shoppingCart;

    @Column(name ="courses_id")
    String courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    Course course;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @Version
    private int version;
}
