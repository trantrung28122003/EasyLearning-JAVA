import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';

class SignUpFormWidget extends StatelessWidget {
  const SignUpFormWidget({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: tFormHeight - 10),
      child: Form(
          child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          TextFormField(
            decoration: const InputDecoration(
                label: Text(tFullName),
                prefixIcon: Icon(Icons.person_outline_rounded)),
          ),
          const SizedBox(height: tFormHeight - 20),
          TextFormField(
            decoration: const InputDecoration(
                label: Text(tEmail), prefixIcon: Icon(Icons.email_outlined)),
          ),
          const SizedBox(height: tFormHeight - 20),
          TextFormField(
            decoration: const InputDecoration(
                label: Text(tPhoneNo), prefixIcon: Icon(Icons.numbers)),
          ),
          const SizedBox(height: tFormHeight - 20),
          TextFormField(
            decoration: const InputDecoration(
                prefixIcon: Icon(Icons.fingerprint),
                labelText: tPassword,
                hintText: tPassword,
                border: OutlineInputBorder(),
                suffixIcon: IconButton(
                    onPressed: null, icon: Icon(Icons.remove_red_eye_sharp))),
          ),
          const SizedBox(height: tFormHeight - 10),
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
                onPressed: () {}, child: Text(tSignup.toUpperCase())),
          )
        ],
      )),
    );
  }
}
