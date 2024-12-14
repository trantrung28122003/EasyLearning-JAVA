import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/commom/form/form_header_widget.dart';
import 'package:flutter_application_elearning/src/constants/image_string.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/signup/signup_footer_widget.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/signup/signup_form_widget.dart';

class SignUpScreen extends StatelessWidget {
  const SignUpScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Container(
            child: const Column(
              children: [
                FormHeaderWidget(
                  image: tSplashImage,
                  title: TTexts.tSignUpTitle,
                  subTitle: TTexts.tSignUpSubTitle,
                ),
                SignUpFormWidget(),
                SignUpfooterWidget()
              ],
            ),
          ),
        ),
      ),
    );
  }
}
