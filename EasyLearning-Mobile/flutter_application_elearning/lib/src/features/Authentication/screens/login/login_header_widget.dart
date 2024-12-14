import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/image_string.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';

class LoginHeaderWidget extends StatelessWidget {
  const LoginHeaderWidget({
    super.key,
  });
  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    return Column(
      children: [
        Image(
          image: const AssetImage(tSplashImage),
          height: size.height * 0.2,
        ),
        Text(TTexts.tLoginTitle,
            style: Theme.of(context).textTheme.headlineLarge),
        Text(TTexts.tLoginSubTitle,
            style: Theme.of(context).textTheme.bodyLarge),
      ],
    );
  }
}
