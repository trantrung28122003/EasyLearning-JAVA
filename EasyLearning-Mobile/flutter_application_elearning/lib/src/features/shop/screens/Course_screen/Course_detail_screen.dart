import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/API/api_service.dart';
import 'package:intl/intl.dart';
import 'package:flutter_application_elearning/src/Model/Course.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:flutter_application_elearning/src/constants/token_manager.dart';

class CourseDetailScreen extends StatelessWidget {
  final Course course;

  const CourseDetailScreen({super.key, required this.course});

  // Hàm xử lý và định dạng ngày tháng
  String _formatDate(dynamic date) {
    try {
      if (date == null || (date is String && date.isEmpty)) {
        return 'Chưa xác định';
      }
      if (date is DateTime) {
        return DateFormat('dd/MM/yyyy').format(date);
      }
      if (date is String) {
        String formattedDate = date.replaceAll('T', ' ');
        DateTime parsedDate = DateTime.parse(formattedDate);
        return DateFormat('dd/MM/yyyy').format(parsedDate);
      }
      return 'Chưa xác định';
    } catch (e) {
      print("Error parsing date: $e");
      return 'Chưa xác định';
    }
  }

  Future<void> addToCart(BuildContext context) async {
    final token = await TokenManager.getToken();
    debugPrint("check token: $token");
    try {
      // In ra dữ liệu trước khi gửi
      print("Data to be sent: ");
      print({
        'courseId': course.id,
        'cartItemName': course.courseName,
        'imageUrl': course.coursesImageUrl,
        'cartItemPrice': course.coursePrice.toString(),
        'cartItemPriceDiscount': course.coursePrice
            .toString(), // Assuming no discount, adjust as needed
        'quantity': 1, // Assuming default quantity of 1, adjust if needed
        'dateCreate': DateTime.now().toIso8601String(),
        'dateChange': DateTime.now().toIso8601String(),
        'changedBy': 'user',
        'isDeleted': false,
        'shoppingCartId': '', // Assuming empty for now, adjust if needed
        // Assuming version is 0, adjust if needed
      });

      final response = await APIService.addToCart({
        'courseId': course.id,
        'cartItemName': course.courseName,
        'imageUrl': course.coursesImageUrl,
        'cartItemPrice': course.coursePrice,
        'cartItemPriceDiscount': course.coursePrice, // Assuming no discount
        'quantity': null,
        'dateCreate': DateTime.now().toIso8601String(),
        'dateChange': DateTime.now().toIso8601String(),
        'changedBy': 'user',
        'isDeleted': false,
        'shoppingCartId': '', // Adjust as needed
        // Adjust as needed
      });

      print("Response from addToCart API: ${response.toString()}");

      if (response != null && response['success'] == true) {
        debugPrint('Item added to cart successfully!');
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Khóa học đã được thêm vào giỏ hàng!')),
        );
      } else {
        final errorMessage = response?['message'] ?? 'Unknown error';
        debugPrint('Error adding item to cart: $errorMessage');
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Thêm vào giỏ hàng thất bại: $errorMessage'),
          ),
        );
      }
    } catch (e) {
      print('Error adding item to cart: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Đã xảy ra lỗi khi thêm vào giỏ hàng.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        // title: Text(course.courseName ?? 'Chi tiết khóa học'),
        backgroundColor: const Color(0xFF0055B8),
        elevation: 0,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Card chứa tên khóa học
            Card(
              margin: const EdgeInsets.only(bottom: 16),
              elevation: 8,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(16),
              ),
              color: Colors.blue[50],
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  course.courseName ?? 'Không rõ',
                  style: const TextStyle(
                    fontSize: 26,
                    fontWeight: FontWeight.bold,
                    color: Color(0xFF0055B8),
                  ),
                ),
              ),
            ),

            // Giảng viên
            _buildInfoRow('Giảng viên', course.courseInstructor ?? 'Không rõ'),

            // Mô tả khóa học
            _buildInfoRow(
                'Mô tả khóa học', course.courseDescription ?? 'Không có mô tả'),

            // Nội dung khóa học
            _buildInfoRow('Nội dung khóa học',
                course.courseContent ?? 'Không có nội dung'),

            // Giá khóa học
            _buildInfoRow(
                'Giá khóa học', '${course.coursePrice ?? 'Miễn phí'} VND'),

            // Loại khóa học
            _buildInfoRow('Loại khóa học', course.courseType ?? 'Không rõ'),

            // Hình ảnh khóa học
            if (course.coursesImageUrl.isNotEmpty)
              Center(
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(16),
                  child: Image.network(
                    course.coursesImageUrl,
                    height: 220,
                    width: double.infinity,
                    fit: BoxFit.cover,
                    errorBuilder: (context, error, stackTrace) => const Icon(
                      Icons.broken_image,
                      size: 100,
                      color: Colors.grey,
                    ),
                  ),
                ),
              ),
            const SizedBox(height: 30),

            // Nút thêm vào giỏ hàng
            Center(
              child: ElevatedButton(
                onPressed: () => addToCart(context),
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFF0055B8),
                  padding:
                      const EdgeInsets.symmetric(vertical: 12, horizontal: 24),
                ),
                child: const Text(
                  'Thêm vào giỏ hàng',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  // Phương thức xây dựng thông tin
  Widget _buildInfoRow(String title, String value) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            '$title: ',
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: Color(0xFF0055B8),
            ),
          ),
          Expanded(
            child: Text(
              value,
              style: const TextStyle(fontSize: 14, color: Colors.black87),
              softWrap: true,
            ),
          ),
        ],
      ),
    );
  }
}
