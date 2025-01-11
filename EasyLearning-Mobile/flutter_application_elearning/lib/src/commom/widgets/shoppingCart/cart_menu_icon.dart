import 'package:flutter/material.dart';
import 'package:iconsax/iconsax.dart';

class TCartCounterIcon extends StatelessWidget {
  const TCartCounterIcon(
      {super.key, required this.onPress, this.iconColor = Colors.white});

  final Color? iconColor;
  final VoidCallback onPress;
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        IconButton(
            onPressed: onPress,
            icon: Icon(
              Iconsax.shopping_bag,
              color: iconColor,
            )),
        Positioned(
          right: 0,
          child: SizedBox(
            width: 18,
            height: 18,
            // decoration: BoxDecoration(
            //   color: Colors.black,
            //   borderRadius: BorderRadius.circular(100),
            // ),
            child: Center(
              child: Text(
                '',
                style: Theme.of(context)
                    .textTheme
                    .labelLarge!
                    .apply(color: Colors.white, fontSizeFactor: 0.9),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
