import 'package:shared_preferences/shared_preferences.dart';

class TokenManager {
  static const String _keyToken = "auth_token"; // Key duy nhất để lưu token

  // Lưu token vào SharedPreferences
  static Future<void> saveToken(String token) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString(_keyToken, token); // Đảm bảo sử dụng cùng một key
    print("Token saved: $token");
  }

  // Lấy token từ SharedPreferences
  static Future<String?> getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString(_keyToken); // Key nhất quán
  }

  // Xóa token khỏi SharedPreferences
  static Future<void> deleteToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.remove(_keyToken); // Key nhất quán
  }
}
