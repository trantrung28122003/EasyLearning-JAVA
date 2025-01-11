import 'dart:io';

class AuthenticationModel {
  // Fields dùng chung cho LoginRequest và RegisterRequest
  final String? userName;
  final String? password;

  // Fields chỉ dùng cho RegisterRequest
  final String? email;
  final String? fullName;
  final String? dayOfBirth;
  final String? confirmPassword;
  final bool? termAndConditions;
  final File? file;
  final String? imageName;

  // Fields chỉ dùng cho LoginResponse
  final bool? authenticated;
  final String? token;

  AuthenticationModel({
    // Dùng cho LoginRequest
    this.userName,
    this.password,
    // Dùng cho RegisterRequest
    this.email,
    this.fullName,
    this.dayOfBirth,
    this.confirmPassword,
    this.termAndConditions,
    this.file,
    this.imageName,
    // Dùng cho LoginResponse
    this.authenticated,
    this.token,
  });

  // Tạo đối tượng từ JSON (dùng cho LoginResponse)
  factory AuthenticationModel.fromJson(Map<String, dynamic> json) {
    return AuthenticationModel(
      authenticated: json['authenticated'],
      token: json['token'],
    );
  }

  // Chuyển đối tượng thành JSON (dùng cho LoginRequest hoặc RegisterRequest)
  Map<String, dynamic> toJson({bool isRegister = false}) {
    if (isRegister) {
      return {
        'userName': userName,
        'email': email,
        'fullName': fullName,
        'dayOfBirth': dayOfBirth,
        'file': file != null ? file!.path : null,
        'password': password,
        'confirmPassword': confirmPassword,
        'termAndConditions': termAndConditions,
        'imageName': imageName,
      };
    } else {
      return {
        'userName': userName,
        'password': password,
      };
    }
  }
}
