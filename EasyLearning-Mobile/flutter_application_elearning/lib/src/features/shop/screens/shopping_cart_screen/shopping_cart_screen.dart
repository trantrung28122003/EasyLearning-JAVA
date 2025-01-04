import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_application_elearning/src/Model/Course.dart'; // Import mô hình Course từ file riêng

// Hàm lấy danh sách các khóa học từ API
Future<List<Course>> fetchCourses() async {
  final url = Uri.parse('http://10.0.2.2/export_json.php'); // Thay URL đúng
  final response = await http.get(url);

  if (response.statusCode == 200) {
    List<dynamic> jsonResponse = json.decode(response.body);
    return jsonResponse
        .map((courseJson) => Course.fromJson(courseJson))
        .toList();
  } else {
    throw Exception(
        'Failed to load courses. HTTP Status Code: ${response.statusCode}');
  }
}

// Màn hình giỏ hàng
class ShoppingCartScreen extends StatefulWidget {
  @override
  _ShoppingCartScreenState createState() => _ShoppingCartScreenState();
}

class _ShoppingCartScreenState extends State<ShoppingCartScreen> {
  late Future<List<Course>> _futureCourses;

  @override
  void initState() {
    super.initState();
    _futureCourses = fetchCourses();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Giỏ Hàng'),
      ),
      body: FutureBuilder<List<Course>>(
        future: _futureCourses,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(child: Text('No courses available.'));
          } else {
            final courses = snapshot.data!;
            return ListView.builder(
              itemCount: courses.length,
              itemBuilder: (context, index) {
                final course = courses[index];
                return Card(
                  margin: EdgeInsets.all(8),
                  child: ListTile(
                    leading: course.coursesImageUrl.isNotEmpty
                        ? Image.network(course.coursesImageUrl,
                            width: 50, height: 50, fit: BoxFit.cover)
                        : Icon(Icons.image, size: 50),
                    title: Text(course.courseName),
                    subtitle: Text('\$${course.coursePrice}'),
                    trailing: IconButton(
                      icon: Icon(Icons.remove_circle_outline),
                      onPressed: () {
                        // Xử lý xóa khóa học khỏi giỏ
                      },
                    ),
                  ),
                );
              },
            );
          }
        },
      ),
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.all(8.0),
        child: ElevatedButton(
          onPressed: () {
            // Xử lý thanh toán
            showDialog(
              context: context,
              builder: (BuildContext context) {
                return AlertDialog(
                  content: Text('Bạn có chắc là bạn muốn thanh toán không ?'),
                  actions: [
                    TextButton(
                      onPressed: () {
                        Navigator.of(context).pop();
                      },
                      child: Text('Hủy'),
                    ),
                    TextButton(
                      onPressed: () {
                        // Xử lý thanh toán
                      },
                      child: Text('Thanh Toán'),
                    ),
                  ],
                );
              },
            );
          },
          child: Text('Mua'),
        ),
      ),
    );
  }
}

void main() {
  runApp(MaterialApp(
    home: ShoppingCartScreen(),
  ));
}
