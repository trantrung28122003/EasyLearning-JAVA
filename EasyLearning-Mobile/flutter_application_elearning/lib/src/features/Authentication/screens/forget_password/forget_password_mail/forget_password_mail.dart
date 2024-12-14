import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/commom/form/form_header_widget.dart';
import 'package:flutter_application_elearning/src/constants/image_string.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/forget_password/forget_password_otp/otp_screen.dart';
import 'package:get/get.dart';

class ForgetPasswordMailScreen extends StatelessWidget {
  const ForgetPasswordMailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Container(
            padding: const EdgeInsets.all(tDefaultSize),
            child: Column(
              children: [
                const SizedBox(height: tDefaultSize * 4),
                const FormHeaderWidget(
                  image: tSplashImage,
                  title: TTexts.tForgetPassword,
                  subTitle: TTexts.tForgetPasswordSubTitle,
                ),
                const SizedBox(height: tFormHeight),
                Form(
                  child: Column(
                    children: [
                      TextFormField(
                        decoration: const InputDecoration(
                          label: Text(TTexts.tEmail),
                          hintText: TTexts.tEmail,
                          prefixIcon: Icon(Icons.mail_outline_rounded),
                        ),
                      ),
                      const SizedBox(height: 20.0),
                      SizedBox(
                        width: double.infinity,
                        child: ElevatedButton(
                            onPressed: () {
                              Get.to(() => const OTPScreen());
                            },
                            child: const Text(TTexts.tNext)),
                      ),
                    ],
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
