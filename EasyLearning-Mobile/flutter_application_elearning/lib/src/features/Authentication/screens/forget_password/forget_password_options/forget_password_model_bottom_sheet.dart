import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/forget_password/forget_password_mail/forget_password_mail.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/forget_password/forget_password_options/forget_password_btn_widget.dart';
import 'package:get/get.dart';

class ForgetPasswordScreen {
  static Future<dynamic> buildShowModalBottomSheet(BuildContext context) {
    return showModalBottomSheet(
      context: context,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
      builder: (context) => Container(
        padding: const EdgeInsets.all(tDefaultSize),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(TTexts.tForgetPasswordTitle,
                style: Theme.of(context).textTheme.headlineLarge),
            Text(TTexts.tForgetPasswordSubTitle,
                style: Theme.of(context).textTheme.bodyLarge),
            const SizedBox(
              height: 30.0,
            ),
            ForgetPasswordBtnWidget(
              btnIcon: Icons.mail_outline_rounded,
              title: TTexts.tEmail,
              subTitle: TTexts.tResetViaEMail,
              onTap: () {
                Navigator.pop(context);
                Get.to(() => const ForgetPasswordMailScreen());
              },
            ),
            const SizedBox(height: 30.0),
            ForgetPasswordBtnWidget(
              btnIcon: Icons.mobile_friendly_rounded,
              title: TTexts.tPhoneNo,
              subTitle: TTexts.tResetViaPhone,
              onTap: () {},
            ),
            const SizedBox(
              height: 30.0,
            ),
          ],
        ),
      ),
    );
  }
}
