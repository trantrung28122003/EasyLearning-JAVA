import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';
import 'package:flutter_application_elearning/src/features/Authentication/screens/forget_password/forget_password_options/forget_password_model_bottom_sheet.dart';

class LoginForm extends StatelessWidget {
  const LoginForm({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Form(
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: tFormHeight - 15),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextFormField(
              decoration: const InputDecoration(
                  prefixIcon: Icon(Icons.person_outline_outlined),
                  labelText: TTexts.tEmail,
                  hintText: TTexts.tEmail,
                  border: OutlineInputBorder()),
            ),
            const SizedBox(height: tFormHeight - 20),
            TextFormField(
              decoration: const InputDecoration(
                  prefixIcon: Icon(Icons.fingerprint),
                  labelText: TTexts.tPassword,
                  hintText: TTexts.tPassword,
                  border: OutlineInputBorder(),
                  suffixIcon: IconButton(
                      onPressed: null, icon: Icon(Icons.remove_red_eye_sharp))),
            ),
            const SizedBox(
              height: tFormHeight - 20,
            ),
            Align(
              alignment: Alignment.centerRight,
              child: TextButton(
                onPressed: () {
                  ForgetPasswordScreen.buildShowModalBottomSheet(context);
                },
                child: const Text(TTexts.tForgetPassword),
              ),
            ),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () {},
                child: Text(TTexts.tLogin.toUpperCase()),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
