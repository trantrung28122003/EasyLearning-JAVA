import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/commom/widgets/appbar/appbar.dart';
import 'package:flutter_application_elearning/src/commom/widgets/shoppingCart/cart_menu_icon.dart';
import 'package:flutter_application_elearning/src/constants/text_strings.dart';

class THomeAppBar extends StatelessWidget {
  const THomeAppBar({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return TAppBar(
      title: const Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        // children: [
        //   Text(TTexts.tHomeAppBarTitle,
        //       style: Theme.of(context)
        //           .textTheme
        //           .labelMedium!
        //           .apply(color: Colors.grey)),
        //   Text(TTexts.tHomeAppBarSubTitle,
        //       style: Theme.of(context)
        //           .textTheme
        //           .labelSmall!
        //           .apply(color: Colors.white)),
        // ],
      ),
      actions: [
        TCartCounterIcon(
          onPress: () {},
        )
      ],
    );
  }
}
