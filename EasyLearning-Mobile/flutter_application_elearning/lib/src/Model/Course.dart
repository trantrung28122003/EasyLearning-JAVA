import 'dart:convert';
import 'package:http/http.dart' as http;

class Course {
  final String id;
  final String courseContent;
  final String courseDescription;
  final String courseName;
  final double coursePrice; // Sửa từ String thành double
  final String courseType;
  final String courseEndDate;
  final String coursesImageUrl;
  final String courseInstructor;
  final String courseStartDate;

  const Course({
    required this.id,
    required this.courseContent,
    required this.courseDescription,
    required this.courseName,
    required this.coursePrice,
    required this.courseType,
    required this.courseEndDate,
    required this.coursesImageUrl,
    required this.courseInstructor,
    required this.courseStartDate,
  });

  factory Course.fromJson(Map<String, dynamic> json) {
    return Course(
      id: json['id'] ?? '',
      courseContent: json['courseContent'] ?? 'No content available',
      courseDescription: json['courseDescription'] ?? 'No description',
      courseName: json['courseName'] ?? 'Unknown',
      // Kiểm tra giá trị và chuyển kiểu nếu cần
      coursePrice: json['coursePrice'] is double
          ? json['coursePrice']
          : double.tryParse(json['coursePrice'].toString()) ??
              0.0, // Chuyển kiểu nếu là string
      courseType: json['courseType'] ?? 'Unknown',
      courseEndDate: json['courseEndDate'] ?? '',
      coursesImageUrl: json['imageUrl'] ?? '',
      courseInstructor: json['instructor'] ?? 'Unknown instructor',
      courseStartDate: json['courseStartDate'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'courseContent': courseContent,
      'courseDescription': courseDescription,
      'courseName': courseName,
      'coursePrice': coursePrice,
      'courseType': courseType,
      'courseEndDate': courseEndDate,
      'coursesImageUrl': coursesImageUrl,
      'courseInstructor': courseInstructor,
      'courseStartDate': courseStartDate,
    };
  }
}



// Hàm lấy danh sách các khóa học từ API
// Future<List<Course>> fetchCourses() async {
//   final url = Uri.parse('http://localhost/export_json.php'); // Thay URL đúng
//   final response = await http.get(url);

//   if (response.statusCode == 200) {
//     List<dynamic> jsonResponse = json.decode(response.body);
//     return jsonResponse
//         .map((courseJson) => Course.fromJson(courseJson))
//         .toList();
//   } else {
//     throw Exception(
//         'Failed to load courses. HTTP Status Code: ${response.statusCode}');
//   }
// }

// void main() async {
//   try {
//     List<Course> courses = await fetchCourses();

//     // In thông tin từng khóa học
//     for (var course in courses) {
//       print(course);
//     }
//   } catch (e) {
//     print('Error: $e');
//   }
// }
