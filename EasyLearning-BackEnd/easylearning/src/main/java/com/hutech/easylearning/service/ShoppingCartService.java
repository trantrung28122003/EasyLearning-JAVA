package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.ShoppingCartResponse;
import com.hutech.easylearning.entity.ShoppingCart;
import com.hutech.easylearning.entity.ShoppingCartItem;
import com.hutech.easylearning.entity.User;
import com.hutech.easylearning.repository.ShoppingCartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingCartService {

    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCartItemService shoppingCartItemService;

    @Transactional(readOnly = true)
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ShoppingCart getShoppingCartById(String id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found with id: " + id));
    }

    public BigDecimal calculateTotalPrice(List<ShoppingCartItem> shoppingCartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ShoppingCartItem item : shoppingCartItems) {
            totalPrice = totalPrice.add(item.getCartItemPrice());
        }
        return totalPrice;
    }

    @Transactional(readOnly = true)
    public ShoppingCartResponse getShoppingCartByCurrentUser() {
        var currentUser = userService.getMyInfo();
        var shoppingCart = shoppingCartRepository.findShoppingCartByUserId(currentUser.getId());
        var shoppingCartItems = shoppingCartItemService.getShoppingCartItemByShoppingCart(shoppingCart.getId());
        BigDecimal totalPrice = calculateTotalPrice(shoppingCartItems);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCartRepository.save(shoppingCart);

        ShoppingCartResponse shoppingCartResponse = ShoppingCartResponse.builder()
                .id(shoppingCart.getId())
                .totalPrice(shoppingCart.getTotalPrice())
                .userId(shoppingCart.getUserId())
                .shoppingCartItems(shoppingCartItems)
                .dateCreate(shoppingCart.getDateCreate())
                .dateChange(shoppingCart.getDateChange())
                .changedBy(shoppingCart.getChangedBy())
                .isDeleted(shoppingCart.isDeleted())
                .build();

        return shoppingCartResponse;
    }

    @Transactional
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public void deleteShoppingCart(String id) {
        shoppingCartRepository.deleteById(id);
    }
    @Transactional
    public void softDeleteShoppingCart(String id) {
        ShoppingCart shoppingCart = getShoppingCartById(id);
        shoppingCart.setDeleted(true);
        shoppingCartRepository.save(shoppingCart);
    }

}

