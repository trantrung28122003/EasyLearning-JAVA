import 'package:flutter_application_elearning/src/Model/shoppingCartItem.dart';

class ShoppingCart {
  final String id;
  final double totalPrice;
  final List<ShoppingCartItem> shoppingCartItemResponses;
  final double totalPriceDiscount;
  final String userId;
  final bool deleted;

  // Constructor
  ShoppingCart({
    required this.id,
    required this.totalPrice,
    required this.shoppingCartItemResponses,
    required this.totalPriceDiscount,
    required this.userId,
    required this.deleted,
  });

  // Factory method to create ShoppingCart from JSON
  factory ShoppingCart.fromJson(Map<String, dynamic> json) {
    var list = json['shoppingCartItemResponses'] as List;
    List<ShoppingCartItem> itemList =
        list.map((i) => ShoppingCartItem.fromJson(i)).toList();

    return ShoppingCart(
      id: json['id'],
      totalPrice: json['totalPrice'].toDouble(),
      shoppingCartItemResponses: itemList,
      totalPriceDiscount: json['totalPriceDiscount'].toDouble(),
      userId: json['userId'],
      deleted: json['deleted'],
    );
  }

  // Method to convert ShoppingCart to JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'totalPrice': totalPrice,
      'shoppingCartItemResponses':
          shoppingCartItemResponses.map((item) => item.toJson()).toList(),
      'totalPriceDiscount': totalPriceDiscount,
      'userId': userId,
      'deleted': deleted,
    };
  }
}
