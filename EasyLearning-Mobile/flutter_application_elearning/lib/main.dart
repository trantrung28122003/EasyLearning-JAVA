import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/token_manager.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/login/login_screen.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/logout/account_screen.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/welcome/welcome.dart';
import 'package:flutter_application_elearning/src/utils/navigation_menu/navigation_menu.dart';
import 'package:flutter_application_elearning/src/utils/theme/theme.dart';
import 'package:get/get.dart';

void main() async {
  // Chạy hàm check authentication trước khi khởi động app
  WidgetsFlutterBinding
      .ensureInitialized(); // Đảm bảo các dịch vụ đã được khởi tạo
  final token = await TokenManager.getToken();
  runApp(
      MyApp(initialRoute: token != null ? '/NavigationMenu' : '/LoginScreen'));
}

class MyApp extends StatelessWidget {
  final String initialRoute;

  const MyApp({super.key, required this.initialRoute});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      initialRoute: initialRoute, // Dùng route tùy chỉnh
      routes: {
        '/LoginScreen': (context) => const LoginScreen(),
        '/NavigationMenu': (context) => const NavigationMenu(),
        '/AccountScreen': (context) => const AccountScreen(),
      },
      theme: TAppTheme.lightTheme,
      darkTheme: TAppTheme.darkTheme,
      themeMode: ThemeMode.system,
    );
  }
}

class AppHome extends StatelessWidget {
  const AppHome({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(".TranTrung"),
        leading: const Icon(Icons.ondemand_video),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add_shopping_cart_outlined),
        onPressed: () {},
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: ListView(
          children: [
            Text(
              "Heading",
              style: Theme.of(context).textTheme.displayMedium,
            ),
            Text(
              "Sub-heading",
              style: Theme.of(context).textTheme.titleSmall,
            ),
            Text(
              "Sub-heading",
              style: Theme.of(context).textTheme.bodyLarge,
            ),
            ElevatedButton(
              onPressed: () {},
              child: const Text("123213"),
            ),
            OutlinedButton(
              onPressed: () {},
              child: const Text("123213"),
            ),
            const Padding(
              padding: EdgeInsets.all(20.0),
              child: Image(image: AssetImage("assets/images/books.png")),
            ),
          ],
        ),
      ),
    );
  }
}
