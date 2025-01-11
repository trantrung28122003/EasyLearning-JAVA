import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/API/api_service.dart';
import 'package:flutter_application_elearning/src/constants/token_manager.dart';
import 'package:get/get.dart';

class AccountScreen extends StatefulWidget {
  const AccountScreen({super.key});

  @override
  _AccountScreenState createState() => _AccountScreenState();
}

class _AccountScreenState extends State<AccountScreen> {
  String userName = '';
  String email = '';
  String fullName = '';
  String dayOfBirth = '';
  String? imageUrl;

  @override
  void initState() {
    super.initState();
    _loadUserData();
  }

  Future<void> _loadUserData() async {
    final response = await APIService.fetchUserInfo();
    if (response['success']) {
      final userData = response['data']['result'];
      setState(() {
        userName = userData['userName'] ?? '';
        email = userData['email'] ?? '';
        fullName = userData['fullName'] ?? '';
        dayOfBirth = userData['dayOfBirth'] ?? '';
        imageUrl = userData['imageUrl'];
      });
    } else {
      Get.snackbar('Error', response['message'] ?? 'Failed to load user data');
    }
  }

  void _logout() async {
    await TokenManager.deleteToken();
    Get.offAllNamed('/LoginScreen');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Thông tin tài khoản'),
        centerTitle: true,
        backgroundColor: Colors.blueAccent,
        elevation: 4,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            // Avatar section with a smooth border and shadow
            ClipRRect(
              borderRadius: BorderRadius.circular(100),
              child: Container(
                decoration: BoxDecoration(
                  boxShadow: [
                    BoxShadow(
                      color: Colors.black.withOpacity(0.2),
                      spreadRadius: 2,
                      blurRadius: 10,
                      offset: Offset(0, 4), // Shadow position
                    ),
                  ],
                ),
                child: CircleAvatar(
                  radius: 80,
                  backgroundImage: imageUrl != null
                      ? NetworkImage(imageUrl!)
                      : const AssetImage('assets/default_avatar.png')
                          as ImageProvider,
                ),
              ),
            ),
            const SizedBox(height: 20),

            // Info cards with gradient background
            _buildInfoCard(
              icon: Icons.person,
              title: 'Tên đăng nhập',
              value: userName,
            ),
            _buildInfoCard(
              icon: Icons.email,
              title: 'Email',
              value: email,
            ),
            _buildInfoCard(
              icon: Icons.account_circle,
              title: 'Họ và tên',
              value: fullName,
            ),
            _buildInfoCard(
              icon: Icons.cake,
              title: 'Ngày sinh',
              value: dayOfBirth,
            ),
            const SizedBox(height: 40),

            // Logout button with smooth hover effect
            ElevatedButton.icon(
              onPressed: _logout,
              icon: const Icon(Icons.logout, color: Colors.white),
              label: const Text(
                'Đăng xuất',
                style: TextStyle(fontSize: 16),
              ),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.redAccent,
                minimumSize: const Size(double.infinity, 50),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(16),
                ),
                elevation: 5,
                shadowColor: Colors.black.withOpacity(0.3),
              ),
            ),
          ],
        ),
      ),
    );
  }

  // Reusable widget for info cards with gradient background
  Widget _buildInfoCard(
      {required IconData icon, required String title, required String value}) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 12),
      elevation: 6,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      color: Colors.blue[50],
      child: Padding(
        padding: const EdgeInsets.all(18.0),
        child: Row(
          children: [
            Icon(icon, color: Colors.blueAccent, size: 32),
            const SizedBox(width: 20),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  title,
                  style: const TextStyle(
                    fontSize: 14,
                    color: Colors.grey,
                  ),
                ),
                const SizedBox(height: 5),
                Text(
                  value,
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    color: Colors.black87,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
