import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';
import 'package:flutter_otp_text_field/flutter_otp_text_field.dart';
import 'package:google_fonts/google_fonts.dart';

class OTPScreen extends StatelessWidget {
  const OTPScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: const EdgeInsets.all(tDefaultSize),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              TTexts.tOtpTitle.toUpperCase(),
              style: GoogleFonts.montserrat(
                  fontWeight: FontWeight.bold, fontSize: 90.0),
            ),
            Text(TTexts.tOtpSubTitle.toUpperCase(),
                style: Theme.of(context).textTheme.titleLarge),
            const SizedBox(
              height: 40.0,
            ),
            const Text(
              "${TTexts.tOtpMessage}trantrung28122003@gmail.com",
              textAlign: TextAlign.center,
            ),
            const SizedBox(
              height: 20.0,
            ),
            OtpTextField(
              numberOfFields: 6,
              fillColor: Colors.black.withOpacity(0.1),
              filled: true,
              onSubmit: (code) {
                print("Otp is => $code");
              },
            ),
            const SizedBox(
              height: 20.0,
            ),
            SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                    onPressed: () {}, child: const Text(TTexts.tNext))),
          ],
        ),
      ),
    );
  }
}
