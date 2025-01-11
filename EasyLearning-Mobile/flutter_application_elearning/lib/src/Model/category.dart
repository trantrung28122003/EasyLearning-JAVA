import 'package:flutter_application_elearning/src/Model/Course.dart';

class Category {
  String id;
  String categoryName;
  String imageLink;
  int sortOrder;
  String dateCreate;
  String dateChange;
  String changedBy;
  List<Course> courses; // Sử dụng Course thay vì CoursesDetail
  bool deleted;

  Category({
    required this.id,
    required this.categoryName,
    required this.imageLink,
    required this.sortOrder,
    required this.dateCreate,
    required this.dateChange,
    required this.changedBy,
    required this.courses, // Chỉnh lại để chứa danh sách Course
    required this.deleted,
  });

  factory Category.fromJson(Map<String, dynamic> json) {
    return Category(
      id: json['id'],
      categoryName: json['categoryName'],
      imageLink: json['imageLink'] ?? '', // Đảm bảo không null
      sortOrder: json['sortOrder'] ?? 0, // Đảm bảo giá trị mặc định
      dateCreate: json['dateCreate'] ?? '',
      dateChange: json['dateChange'] ?? '',
      changedBy: json['changedBy'] ?? '',
      courses: (json['courses'] as List?)
              ?.map((courseJson) => Course.fromJson(courseJson))
              .toList() ??
          [], // Kiểm tra null và kiểu
      deleted: json['deleted'] ?? false, // Đảm bảo giá trị mặc định
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'categoryName': categoryName,
      'imageLink': imageLink,
      'sortOrder': sortOrder,
      'dateCreate': dateCreate,
      'dateChange': dateChange,
      'changedBy': changedBy,
      'courses': courses.map((course) => course.toJson()).toList(),
      'deleted': deleted,
    };
  }
}
