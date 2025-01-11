class ShoppingCartItem {
  final String id;
  final String cartItemName;
  final String imageUrl;
  final double cartItemPrice;
  final double cartItemPriceDiscount;
  final String shoppingCartId;
  final String courseId;
  final String dateCreate;
  final String dateChange;
  final String changedBy;
  final bool deleted;
  final bool notRegistrable;

  // Constructor
  ShoppingCartItem({
    required this.id,
    required this.cartItemName,
    required this.imageUrl,
    required this.cartItemPrice,
    required this.cartItemPriceDiscount,
    required this.shoppingCartId,
    required this.courseId,
    required this.dateCreate,
    required this.dateChange,
    required this.changedBy,
    required this.deleted,
    required this.notRegistrable,
  });

  // Factory method to create ShoppingCartItem from JSON
  factory ShoppingCartItem.fromJson(Map<String, dynamic> json) {
    return ShoppingCartItem(
      id: json['id'],
      cartItemName: json['cartItemName'],
      imageUrl: json['imageUrl'],
      cartItemPrice: json['cartItemPrice'].toDouble(),
      cartItemPriceDiscount: json['cartItemPriceDiscount'].toDouble(),
      shoppingCartId: json['shoppingCartId'],
      courseId: json['courseId'],
      dateCreate: json['dateCreate'],
      dateChange: json['dateChange'],
      changedBy: json['changedBy'],
      deleted: json['deleted'],
      notRegistrable: json['notRegistrable'],
    );
  }

  // Method to convert ShoppingCartItem to JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'cartItemName': cartItemName,
      'imageUrl': imageUrl,
      'cartItemPrice': cartItemPrice,
      'cartItemPriceDiscount': cartItemPriceDiscount,
      'shoppingCartId': shoppingCartId,
      'courseId': courseId,
      'dateCreate': dateCreate,
      'dateChange': dateChange,
      'changedBy': changedBy,
      'deleted': deleted,
      'notRegistrable': notRegistrable,
    };
  }
}
