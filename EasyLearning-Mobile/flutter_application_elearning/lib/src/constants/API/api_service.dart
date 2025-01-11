import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/Model/Course.dart';
import 'package:flutter_application_elearning/src/Model/category.dart';
import 'package:flutter_application_elearning/src/Model/user.dart';
import 'package:flutter_application_elearning/src/constants/token_manager.dart';
import 'package:http/http.dart' as http;

class APIService {
  static const String BASE_URL = "http://10.0.2.2:8080";

  // static Future<Map<String, dynamic>> getUserProfile() async {
  //   final token = await TokenManager.getToken();
  //   final response = await http.get(
  //     Uri.parse('$BASE_URL/user/profile'),
  //     headers: {
  //       'Authorization': 'Bearer $token',
  //       'Content-Type': 'application/json',
  //     },
  //   );
  //   return response.statusCode == 200
  //       ? jsonDecode(response.body)
  //       : {'success': false, 'message': 'Unauthorized'};
  // }

  // Authentication
  static const String loginUrl = "$BASE_URL/auth/login";
  static const String loginWithGoogleUrl = "$BASE_URL/auth/loginWithGoogle";
  static const String registerUrl = "$BASE_URL/auth/register";

  // User
  static const String getUserInfoUrl = "$BASE_URL/users/myInfo";
  static const String customerUrl = "$BASE_URL/customer";
  static const String updateProfileUrl = "$BASE_URL/customer/updateProfile";
  static const String changePasswordUrl = "$BASE_URL/customer/changePassword";
  static const String forgotPasswordUrl =
      "$BASE_URL/email/sendVerificationCode";
  static const String verifyCodeUrl = "$BASE_URL/email/verifyCode";
  static const String resetPasswordUrl = "$BASE_URL/customer/resetPassword";

  // Event
  static const String eventUrl = "$BASE_URL/courseEvent";
  // Category
  static const String categoryUrl = "$BASE_URL/category";
  static const String createCategoryUrl = "$categoryUrl/create";
  static const String updateCategoryUrl = "$categoryUrl/update";

  // Course
  static const String courseUrl = "$BASE_URL/course";
  static const String getCoursesUrl = "$BASE_URL/courses";
  static const String createCourseUrl = "$courseUrl/create";
  static const String updateCourseUrl = "$courseUrl/update";
  static const String getCourseDetailUrl = "$BASE_URL/detailCourse";
  static const String getCoursesByCategoryUrl =
      "$BASE_URL/getCoursesByCategory";
  static const String searchCoursesUrl = "$BASE_URL/search";

  // Shopping Cart
  static const String shoppingCartUrl = "$BASE_URL/shoppingCart";
  static const String addToCartUrl = "$shoppingCartUrl/addToCart";
  static const String removeFromCartUrl = "$shoppingCartUrl/remove";

  // Payment
  static const String paymentUrl = "$BASE_URL/payment";
  static const String doPaymentMomoUrl = "$paymentUrl/doPaymentMomo";
  static const String confirmPaymentMomoUrl =
      "$paymentUrl/confirmPaymentMomoClient";
  static const String doPaymentVNPayUrl = "$paymentUrl/doPaymentVNPay";
  static const String confirmPaymentVNPayUrl =
      "$paymentUrl/confirmPaymentVNPayClient";

  // Common API call function
  static Future<Map<String, dynamic>> post(
      String url, Map<String, dynamic> body) async {
    try {
      final response = await http.post(
        Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode(body),
      );

      // Kiểm tra mã trạng thái HTTP
      if (response.statusCode == 200 || response.statusCode == 201) {
        return {"success": true, "data": jsonDecode(response.body)};
      } else {
        // Kiểm tra nếu phản hồi không có trường 'message'
        var message = "Error occurred";
        try {
          message = jsonDecode(response.body)['message'] ?? message;
        } catch (e) {
          // Nếu không có message, sử dụng thông báo mặc định
        }
        return {"success": false, "message": message};
      }
    } catch (e) {
      // Xử lý lỗi mạng hoặc kết nối
      return {
        "success": false,
        "message": "Unable to connect to the server: $e"
      };
    }
  }

  static Future<Map<String, dynamic>> get(String url) async {
    try {
      final token = await TokenManager.getToken();
      final response = await http.get(
        Uri.parse(url),
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
          "Authorization": "Bearer $token"
        },
      );

      if (response.statusCode == 200) {
        // Giải mã lại các chuỗi nếu bị lỗi font
        String decodedBody = utf8.decode(response.bodyBytes);
        return {"success": true, "data": jsonDecode(decodedBody)};
      } else {
        var message = "Error occurred";
        try {
          message = jsonDecode(response.body)['message'] ?? message;
        } catch (e) {
          // Nếu không có message, sử dụng thông báo mặc định
        }
        return {"success": false, "message": message};
      }
    } catch (e) {
      return {"success": false, "message": "Unable to connect to the server"};
    }
  }

  // Hàm lấy giỏ hàng
  static Future<List<Map<String, dynamic>>> getShoppingCart() async {
    try {
      final token = await TokenManager.getToken(); // Lấy token từ local storage
      final response = await http.get(
        Uri.parse(shoppingCartUrl),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token', // Thêm token vào header
        },
      );

      // Kiểm tra phản hồi
      if (response.statusCode == 200) {
        final data = json.decode(response.body);

        // Kiểm tra cấu trúc và kiểu dữ liệu
        if (data is Map && data['success'] == true && data['data'] is List) {
          return List<Map<String, dynamic>>.from(data['data']);
        } else {
          throw Exception(data['message'] ?? 'Failed to fetch cart data');
        }
      } else {
        throw Exception('Failed to fetch cart data: ${response.statusCode}');
      }
    } catch (e) {
      print("Error fetching cart data: $e");
      throw Exception('Error occurred while fetching cart data');
    }
  }

  // Hàm thêm vào giỏ hàng
  static Future<Map<String, dynamic>> addToCart(
      Map<String, dynamic> body) async {
    try {
      final token = await TokenManager.getToken(); // Lấy token từ storage
      if (token == null || token.isEmpty) {
        return {'success': false, 'message': 'Token is missing or invalid'};
      }

      print(
          "Sending courseId: ${body['courseId']}"); // In ra courseId trước khi gửi

      final response = await http.post(
        Uri.parse(addToCartUrl + "?courseId=${body['courseId']}"),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token', // Gửi token trong header
        },
        body: json.encode(body['courseId']), // Gửi body đã sửa
      );

      print("Response from addToCart API: ${response.body}");

      if (response.statusCode == 200 || response.statusCode == 201) {
        return {'success': true, 'data': json.decode(response.body)};
      } else {
        try {
          final responseData = json.decode(response.body);
          return {
            'success': false,
            'message': responseData['error'] ?? 'Failed to add item to cart',
          };
        } catch (e) {
          return {'success': false, 'message': 'Invalid response format'};
        }
      }
    } catch (e) {
      print("Error adding to cart: $e");
      return {'success': false, 'message': 'Error occurred: $e'};
    }
  }

  // Hàm xóa mục khỏi giỏ hàng
  static Future<Map<String, dynamic>> removeFromCart(String cartItemId) async {
    try {
      final response = await http.delete(
        Uri.parse('$removeFromCartUrl/$cartItemId'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200 || response.statusCode == 204) {
        return {'success': true, 'message': 'Xóa thành công'};
      } else {
        return {
          'success': false,
          'message': 'Không thể xóa mục khỏi giỏ hàng',
        };
      }
    } catch (e) {
      print("Error removing item from cart: $e");
      return {'success': false, 'message': 'Lỗi xảy ra khi kết nối với server'};
    }
  }

  static Future<Map<String, dynamic>> searchCourses(String query) async {
    try {
      print("Search query: $query"); // Kiểm tra từ khóa gửi đi
      final response = await get('$searchCoursesUrl?q=$query');

      print("Raw Response: $response"); // Kiểm tra phản hồi từ server

      // Kiểm tra nếu response có chứa key 'success' và 'data' không null
      if (response != null && response['success'] && response['data'] != null) {
        var data = response['data'];

        // Kiểm tra 'data' có phải là một Map và có key 'result' là List
        if (data is Map && data['result'] is List) {
          var result = data['result'];

          // In ra kết quả để kiểm tra
          print("Filtered Result: $result");

          // Trả về dữ liệu với đúng định dạng
          return {'success': true, 'data': result};
        } else {
          // Trường hợp không phải Map hoặc không có 'result' là List
          return {'success': false, 'message': 'Invalid data format'};
        }
      } else {
        // Trường hợp không có dữ liệu hoặc có lỗi ở response
        return {'success': false, 'message': 'Invalid response from server'};
      }
    } catch (e) {
      print("Search error: $e");
      return {'success': false, 'message': 'Unable to connect to the server'};
    }
  }

  // Specific API functions
  static Future<Map<String, dynamic>> login(
      String userName, String password) async {
    try {
      final response = await http.post(
        Uri.parse(loginUrl),
        body: jsonEncode({
          'userName': userName,
          'password': password,
        }),
        headers: {
          'Content-Type': 'application/json',
        },
      );

      print("Status code : ${response.statusCode}");

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);

        // Kiểm tra 'code' và 'result' từ phản hồi
        if (responseData['code'] == 200) {
          final token = responseData['result']['token'];
          final authenticated = responseData['result']['authenticated'];

          if (token != null && authenticated == true) {
            print("return 200200: ${response.statusCode}");
            return {
              'success': true,
              'message': responseData['message'] ?? 'Login successful!',
              'token': token,
            };
          } else {
            return {
              'success': false,
              'message':
                  'Authentication failed! Missing token or not authenticated.',
            };
          }
        } else {
          return {
            'success': false,
            'message': responseData['message'] ?? 'Login failed!',
          };
        }
      }
      // else if (response.statusCode == 401) {
      //   print("Status code 401 : ${response.statusCode}");
      //   return {
      //     'success': false,
      //     'message': 'Unauthorized: Incorrect username or password.',
      //   };
      // }
      else {
        return {
          'success': false,
          'message': 'Error occurred! Status code: ${response.statusCode}',
        };
      }
    } catch (e) {
      print("Error: $e");
      return {
        'success': false,
        'message': 'Unable to connect to the server. Please try again later.',
      };
    }
  }

  static Future<Map<String, dynamic>> register(
    String email,
    String password,
    String userName,
    String fullName,
    String dayOfBirth,
  ) async {
    try {
      final body = {
        "userName": userName,
        "fullName": fullName,
        "email": email,
        "dayOfBirth": dayOfBirth,
        "password": password,
      };

      print("Sending registration request with data: $body");

      // Gửi yêu cầu POST đến API mà không cần token
      final response = await http.post(
        Uri.parse(registerUrl),
        headers: {
          'Content-Type': 'application/json',
          // Không cần Authorization header cho yêu cầu đăng ký
        },
        body: jsonEncode(body),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        final responseData = json.decode(response.body);
        if (responseData['success']) {
          return {
            'success': true,
            'message': responseData['message'] ?? 'Registration successful!',
          };
        } else {
          return {
            'success': false,
            'message': responseData['message'] ?? 'Registration failed!',
          };
        }
      } else {
        return {
          'success': false,
          'message': 'Error occurred! Status code: ${response.statusCode}',
        };
      }
    } catch (e) {
      return {
        'success': false,
        'message': 'Unable to connect to the server: $e',
      };
    }
  }

  // Trong APIService (Thêm các hàm mới)
  static Future<List<Category>> fetchCategories() async {
    final response = await get(categoryUrl);
    print("category: $response");
    if (response['success']) {
      if (response['data'] == null || response['data'] is! List) {
        print("category: $response");
        throw Exception('Categories data is either null or not a List');
      }
      final data = response['data'];
      return data
          .map<Category>((category) => Category.fromJson(category))
          .toList();
    } else {
      throw Exception(response['message'] ?? 'Failed to fetch categories');
    }
  }

  static Future<List<Course>> fetchCourses() async {
    final response = await get(courseUrl);
    print("course: $response");
    if (response['success'] && response['data'] != null) {
      final data = response['data'];
      if (data is List) {
        return data.map((course) => Course.fromJson(course)).toList();
      } else {
        throw Exception(
            "Expected a list of courses, but got ${data.runtimeType}");
      }
    } else if (response['message'] != null) {
      throw Exception(response['message']);
    } else {
      throw Exception('Failed to fetch courses');
    }
  }

  static Future<Map<String, dynamic>> fetchUserInfo() {
    return get(getUserInfoUrl);
  }

  static Future<Map<String, dynamic>> updateProfile(
      Map<String, dynamic> profileData) {
    return post(updateProfileUrl, profileData);
  }

  static Future<Map<String, dynamic>> changePassword(
      String oldPassword, String newPassword) {
    return post(changePasswordUrl, {
      "oldPassword": oldPassword,
      "newPassword": newPassword,
    });
  }
}
