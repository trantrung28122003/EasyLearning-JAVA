import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/Model/Course.dart';

class CourseDetailScreen extends StatelessWidget {
  final Course course;

  // Nhận thông tin khóa học qua constructor
  CourseDetailScreen({required this.course});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(course.courseName ?? 'Chi tiết khóa học'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Tên khóa học: ${course.courseName ?? 'Không rõ'}',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),
            Text(
              'Người hướng dẫn: ${course.courseInstructor ?? 'Không rõ'}',
              style: TextStyle(fontSize: 16),
            ),
            SizedBox(height: 10),
            Text(
              'Mô tả: ${course.courseDescription ?? 'Không có mô tả'}',
              style: TextStyle(fontSize: 14),
            ),
            SizedBox(height: 10),
            Text(
              'Nội dung khóa học: ${course.courseContent ?? 'Không có nội dung'}',
              style: TextStyle(fontSize: 14),
            ),
            SizedBox(height: 10),
            Text(
              'Giá khóa học: ${course.coursePrice ?? 'Miễn phí'} VND',
              style: TextStyle(fontSize: 14),
            ),
            SizedBox(height: 10),
            Text(
              'Loại khóa học: ${course.courseType ?? 'Không rõ'}',
              style: TextStyle(fontSize: 14),
            ),
            SizedBox(height: 10),
            Text(
              'Ngày bắt đầu: ${course.courseStartDate ?? 'Chưa xác định'}',
              style: TextStyle(fontSize: 14),
            ),
            SizedBox(height: 10),
            Text(
              'Ngày kết thúc: ${course.courseEndDate ?? 'Chưa xác định'}',
              style: TextStyle(fontSize: 14),
            ),
            SizedBox(height: 10),
            // Thêm hình ảnh khóa học nếu URL có giá trị
            if (course.coursesImageUrl != null &&
                course.coursesImageUrl!.isNotEmpty)
              Center(
                child: Image.network(
                  course.coursesImageUrl!,
                  height: 200,
                  fit: BoxFit.cover,
                  errorBuilder: (context, error, stackTrace) => Icon(
                    Icons.broken_image,
                    size: 100,
                    color: Colors.grey,
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
