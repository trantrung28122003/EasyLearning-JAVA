import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/color.dart';
import 'package:flutter_application_elearning/src/constants/image_string.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/login/login_screen.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/signup/signup.dart';
import 'package:get/get.dart';

class WelcomeScreen extends StatelessWidget {
  const WelcomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    var mediaQuery = MediaQuery.of(context);
    var height = mediaQuery.size.height;
    var brightness = mediaQuery.platformBrightness;
    final isDarkMode = brightness == Brightness.dark;

    return Scaffold(
        backgroundColor: isDarkMode ? tSecondaryColor : tPrimaryColor,
        body: Container(
          padding: const EdgeInsets.all(tDefaultSize),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Image(
                  image: const AssetImage(tSplashImage), height: height * 0.6),
              Text(
                "Chào mừng bạn đến với eLearning",
                style: Theme.of(context).textTheme.headlineMedium,
                textAlign: TextAlign.center,
              ),
              Text(
                "Bạn có thể đăng kí toàn khaorn bên dưới hoặc đăng nhập nếu có tài khoản",
                style: Theme.of(context).textTheme.bodyLarge,
                textAlign: TextAlign.center,
              ),
              Row(
                children: [
                  Expanded(
                    child: OutlinedButton(
                      onPressed: () => Get.to(() => const LoginScreen()),
                      child: Text(tLogin.toUpperCase()),
                    ),
                  ),
                  const SizedBox(width: 10.0),
                  Expanded(
                    child: ElevatedButton(
                      onPressed: () => Get.to(() => const SignUpScreen()),
                      child: Text(tSignup.toUpperCase()),
                    ),
                  ),
                ],
              )
            ],
          ),
        ));
  }
}
