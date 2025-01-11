import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/API/api_service.dart';

class ShoppingCartScreen extends StatefulWidget {
  @override
  _ShoppingCartScreenState createState() => _ShoppingCartScreenState();
}

class _ShoppingCartScreenState extends State<ShoppingCartScreen> {
  List<Map<String, dynamic>> cartItems = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchCartData();
  }

  Future<void> fetchCartData() async {
    try {
      // Gọi API để lấy dữ liệu giỏ hàng
      final items = await APIService.getShoppingCart();

      // Kiểm tra dữ liệu trả về
      if (items == null || items is! List) {
        throw Exception("Invalid data format: Expected a list but got $items");
      }

      setState(() {
        cartItems = List<Map<String, dynamic>>.from(items);
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      print("Error fetching cart data: $e");
    }
  }

  Future<void> removeItemFromCart(String courseId) async {
    try {
      // Giả sử APIService.removeFromCart() sẽ xử lý việc xóa mục khỏi giỏ hàng
      await APIService.removeFromCart(courseId);
      setState(() {
        cartItems.removeWhere((item) => item['courseId'] == courseId);
      });
      print("Item removed from cart.");
    } catch (e) {
      print("Error removing item from cart: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Shopping Cart"),
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : cartItems.isEmpty
              ? Center(child: Text("Your cart is empty"))
              : ListView.builder(
                  itemCount: cartItems.length,
                  itemBuilder: (context, index) {
                    final item = cartItems[index];
                    return ListTile(
                      leading: Image.network(
                        item['courseImage'], // Hình ảnh khóa học
                        width: 50,
                        height: 50,
                        fit: BoxFit.cover,
                      ),
                      title: Text(item['courseName']), // Tên khóa học
                      subtitle:
                          Text("${item['coursePrice']} VND"), // Giá khóa học
                      trailing: IconButton(
                        icon: Icon(Icons.delete),
                        onPressed: () {
                          removeItemFromCart(
                              item['courseId']); // Xóa mục khỏi giỏ hàng
                        },
                      ),
                    );
                  },
                ),
    );
  }
}
