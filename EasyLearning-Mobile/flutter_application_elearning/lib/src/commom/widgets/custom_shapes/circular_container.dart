import 'package:flutter/material.dart';

class TCircularContainer extends StatelessWidget {
  const TCircularContainer({
    super.key,
    this.width = 400,
    this.height = 400,
    this.padding = 0,
    this.radius = 400,
    this.child,
    this.backgroundColor = Colors.white,
  });

  final double? width;
  final double? height;
  final double padding;
  final double radius;
  final Widget? child;
  final Color? backgroundColor;
  @override
  Widget build(BuildContext context) {
    return Container(
      width: 400,
      height: 400,
      padding: const EdgeInsets.all(0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(400),
        color: Colors.white.withOpacity(0.1),
      ),
    );
  }
}
