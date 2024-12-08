import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/constants/color.dart';
import 'package:flutter_application_elearning/src/constants/image_string.dart';
import 'package:flutter_application_elearning/src/constants/sizes.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  bool animate = false;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Stack(children: [
      Positioned(
          top: animate ? 0 : -30,
          left: animate ? 0 : -30,
          child: const Image(image: AssetImage(tSplashImage))),
      Positioned(
        top: 80,
        left: tDefaultSize,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              "Khóa học online",
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            Text(
              "Khóa học online",
              style: Theme.of(context).textTheme.headlineSmall,
            )
          ],
        ),
      ),
      const Positioned(
          bottom: 100, child: Image(image: AssetImage(tSplashImage))),
      Positioned(
          bottom: 40,
          right: tDefaultSize,
          child: Container(
            width: tSplashContainerSize,
            height: tSplashContainerSize,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(100),
              color: tPrimaryColor,
            ),
          )),
    ]));
  }
}
