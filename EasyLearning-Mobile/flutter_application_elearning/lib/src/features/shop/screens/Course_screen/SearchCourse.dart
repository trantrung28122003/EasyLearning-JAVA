import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/Model/Course.dart';
import 'package:flutter_application_elearning/src/constants/API/api_service.dart';
import 'package:flutter_application_elearning/src/features/shop/screens/Course_screen/Course_screen.dart';
import 'package:flutter_application_elearning/src/features/shop/screens/home/widgets/courseitem.dart';

class SearchCourseScreen extends StatefulWidget {
  const SearchCourseScreen({super.key});

  @override
  _SearchCourseScreenState createState() => _SearchCourseScreenState();
}

class _SearchCourseScreenState extends State<SearchCourseScreen> {
  TextEditingController _searchController = TextEditingController();
  List<dynamic> _searchResults = [];
  bool _isLoading = false;

  void _searchCourses() async {
    setState(() {
      _isLoading = true;
    });

    try {
      final response =
          await APIService.searchCourses(_searchController.text.trim());

      setState(() {
        _isLoading = false;

        if (response['success'] && response['data'] is List) {
          _searchResults = response['data']
              .map((courseData) => Course.fromJson(courseData))
              .where((course) {
            return course.courseName != null &&
                course.courseName
                    .toLowerCase()
                    .contains(_searchController.text.trim().toLowerCase());
          }).toList();
        } else {
          _searchResults = [];
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(response['message'] ?? 'No courses found')),
          );
        }
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      print("Error: $e");
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error occurred while searching.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(120),
        child: AppBar(
          backgroundColor: const Color(0xFF6A11CB),
          elevation: 0,
          title: const Text(
            'Tìm kiếm khóa học',
            style: TextStyle(
              fontWeight: FontWeight.bold,
              fontSize: 26,
            ),
          ),
          centerTitle: true,
          shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.only(
              bottomLeft: Radius.circular(30),
              bottomRight: Radius.circular(30),
            ),
          ),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 20.0),
        child: Column(
          children: [
            // Thanh tìm kiếm với hiệu ứng đẹp mắt
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              margin: const EdgeInsets.only(top: 20, bottom: 20),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(30),
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.15),
                    blurRadius: 12,
                    offset: Offset(0, 6),
                  ),
                ],
              ),
              child: TextField(
                controller: _searchController,
                onChanged: (text) {
                  if (text.isNotEmpty) {
                    _searchCourses();
                  } else {
                    setState(() {
                      _searchResults = [];
                    });
                  }
                },
                decoration: InputDecoration(
                  hintText: 'Tìm kiếm...',
                  hintStyle: const TextStyle(color: Colors.grey),
                  border: InputBorder.none,
                  contentPadding: EdgeInsets.symmetric(vertical: 12),
                  suffixIcon: IconButton(
                    icon: const Icon(Icons.search, color: Color(0xFF2575FC)),
                    onPressed: _searchCourses,
                  ),
                ),
              ),
            ),
            // Hiển thị kết quả tìm kiếm
            _isLoading
                ? const Center(child: CircularProgressIndicator())
                : _searchResults.isEmpty
                    ? const Center(child: Text('Không có kết quả'))
                    : Expanded(
                        child: ListView.builder(
                          itemCount: _searchResults.length,
                          itemBuilder: (context, index) {
                            final course = _searchResults[index];
                            return GestureDetector(
                              onTap: () {
                                // Thêm hiệu ứng khi nhấn vào khóa học
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => CourseScreen(),
                                  ),
                                );
                              },
                              child: Padding(
                                padding: const EdgeInsets.only(bottom: 16.0),
                                child: CourseItem(course: course),
                              ),
                            );
                          },
                        ),
                      ),
          ],
        ),
      ),
    );
  }
}
