import 'dart:convert';
import 'package:http/http.dart' as http;

class Course {
  final String id;
  final String courseContent;
  final String courseDescription;
  final String courseName;
  final String coursePrice;
  final String courseType;
  final String courseEndDate;
  final String coursesImageUrl;
  final String courseInstructor;
  final String courseStartDate;

  // Constructor
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

  // Phương thức để khởi tạo đối tượng từ JSON
  factory Course.fromJson(Map<String, dynamic> json) {
    return Course(
      id: json['id'] ?? '',
      courseContent: json['course_content'] ?? 'No content available',
      courseDescription: json['course_description'] ?? 'No description',
      courseName: json['course_name'] ?? 'Unknown',
      coursePrice: json['course_price'] ?? 'Free',
      courseType: json['course_type'] ?? 'Unknown',
      courseEndDate: json['course_end_date'] ?? '',
      coursesImageUrl: json['courses_image_url'] ?? '',
      courseInstructor: json['course_instructor'] ?? 'Unknown instructor',
      courseStartDate: json['course_start_date'] ?? '',
    );
  }

  // Phương thức để chuyển đối tượng thành JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'course_content': courseContent,
      'course_description': courseDescription,
      'course_name': courseName,
      'course_price': coursePrice,
      'course_type': courseType,
      'course_end_date': courseEndDate,
      'courses_image_url': coursesImageUrl,
      'course_instructor': courseInstructor,
      'course_start_date': courseStartDate,
    };
  }

  // Phương thức tiện ích để hiển thị thông tin khi debug
  @override
  String toString() {
    return 'Course(id: $id, name: $courseName, instructor: $courseInstructor)';
  }
}

// Hàm lấy danh sách các khóa học từ API
Future<List<Course>> fetchCourses() async {
  final url = Uri.parse('http://localhost/export_json.php'); // Thay URL đúng
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

void main() async {
  try {
    List<Course> courses = await fetchCourses();

    // In thông tin từng khóa học
    for (var course in courses) {
      print(course);
    }
  } catch (e) {
    print('Error: $e');
  }
}

