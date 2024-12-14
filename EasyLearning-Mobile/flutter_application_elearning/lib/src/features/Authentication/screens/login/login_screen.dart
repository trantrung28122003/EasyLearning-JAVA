import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/commom/form/form_header_widget.dart';
import 'package:flutter_application_elearning/src/constants/image_string.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/login/login_footer_widget.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/login/login_form_widget.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Container(
            child: Container(
              padding: const EdgeInsets.all(tDefaultSize),
              child: const Column(
                children: [
                  FormHeaderWidget(
                    image: tSplashImage,
                    title: TTexts.tLoginTitle,
                    subTitle: TTexts.tLoginSubTitle,
                  ),
                  LoginForm(),
                  LoginFooterWidget(),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
