
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:flutter_application_elearning/src/Model/Course.dart';
import 'Course_detail_screen.dart';
import 'package:iconsax/iconsax.dart'; // Thư viện icon
import 'package:flutter_application_elearning/src/commom/widgets/shoppingCart/cart_menu_icon.dart'; // Import file chứa TCartCounterIcon
import 'package:flutter_application_elearning/src/features/shop/screens/shopping_cart_screen/shopping_cart_screen.dart';

class CourseScreen extends StatefulWidget {
  @override
  _CourseScreenState createState() => _CourseScreenState();
}

class _CourseScreenState extends State<CourseScreen> {
  late Future<List<Course>> courses;

  // Hàm lấy danh sách khóa học
  Future<List<Course>> fetchCourses() async {
    final response = await http.get(
        Uri.parse('http://10.0.2.2/export_json.php')); // Thay URL API của bạn

    if (response.statusCode == 200) {
      List<dynamic> jsonResponse = json.decode(response.body);
      return jsonResponse
          .map((courseJson) => Course.fromJson(courseJson))
          .toList();
    } else {
      throw Exception('Không thể tải danh sách khóa học');
    }
  }

  @override
  void initState() {
    super.initState();
    courses = fetchCourses(); // Gọi hàm lấy dữ liệu khi màn hình được khởi tạo
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Danh sách khóa học'),
      ),
      body: Stack(
        children: [
          FutureBuilder<List<Course>>(
            future: courses,
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(child: CircularProgressIndicator());
              } else if (snapshot.hasError) {
                return Center(child: Text('Lỗi: ${snapshot.error}'));
              } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                return Center(child: Text('Không có khóa học nào.'));
              } else {
                List<Course> courseList = snapshot.data!;

                // Sử dụng GridView.builder để hiển thị danh sách khóa học dưới dạng 2 cột
                return GridView.builder(
                  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: 2, // Số cột
                    mainAxisSpacing: 10, // Khoảng cách dọc giữa các ô
                    crossAxisSpacing: 10, // Khoảng cách ngang giữa các ô
                    childAspectRatio:
                        0.75, // Tỉ lệ chiều rộng / chiều cao của mỗi ô
                  ),
                  padding: const EdgeInsets.all(10),
                  itemCount: courseList.length, // Số lượng phần tử
                  itemBuilder: (context, index) {
                    return GestureDetector(
                      onTap: () {
                        // Điều hướng tới màn hình chi tiết khóa học
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) =>
                                CourseDetailScreen(course: courseList[index]),
                          ),
                        );
                      },
                      child: Card(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        elevation: 5,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            // Hiển thị hình ảnh khóa học
                            ClipRRect(
                              borderRadius: BorderRadius.vertical(
                                  top: Radius.circular(10)), // Bo góc trên
                              child: courseList[index].coursesImageUrl !=
                                          null &&
                                      courseList[index]
                                          .coursesImageUrl!
                                          .isNotEmpty
                                  ? Image.network(
                                      courseList[index].coursesImageUrl!,
                                      height: 100,
                                      width: double.infinity,
                                      fit: BoxFit.cover,
                                      errorBuilder:
                                          (context, error, stackTrace) => Icon(
                                        Icons.broken_image,
                                        size: 50,
                                        color: Colors.grey,
                                      ),
                                    )
                                  : Container(
                                      height: 100,
                                      color: Colors.grey[300],
                                      child: Icon(
                                        Icons.image_not_supported,
                                        size: 50,
                                        color: Colors.grey,
                                      ),
                                    ),
                            ),
                            Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  // Tên khóa học
                                  Text(
                                    courseList[index].courseName ?? 'Không tên',
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 16,
                                    ),
                                    maxLines: 1,
                                    overflow: TextOverflow.ellipsis,
                                  ),
                                  SizedBox(height: 5),
                                  // Giá khóa học
                                  Text(
                                    courseList[index].coursePrice != null
                                        ? '${courseList[index].coursePrice} VND'
                                        : 'Miễn phí',
                                    style: TextStyle(
                                      fontSize: 14,
                                      color: Colors.green,
                                    ),
                                  ),
                                  SizedBox(height: 5),
                                  // Tên giảng viên
                                  Text(
                                    courseList[index].courseInstructor ??
                                        'Không có giảng viên',
                                    style: TextStyle(fontSize: 12),
                                    maxLines: 1,
                                    overflow: TextOverflow.ellipsis,
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    );
                  },
                );
              }
            },
          ),
          // Nút giỏ hàng cố định
          Positioned(
            bottom: 20,
            right: 20,
            child: FloatingActionButton(
              backgroundColor: Colors.blue,
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => ShoppingCartScreen()),
                );
              },
              child: TCartCounterIcon(
                onPress: () {
                  // Hành động khi nhấn vào icon
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => ShoppingCartScreen()),
                  );
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}
