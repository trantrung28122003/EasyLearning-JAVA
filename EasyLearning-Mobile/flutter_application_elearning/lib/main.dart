import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/utils/navigation_menu/navigation_menu.dart';
import 'package:flutter_application_elearning/src/utils/theme/theme.dart';
import 'package:get/get.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
        theme: TAppTheme.lightTheme,
        darkTheme: TAppTheme.darkTheme,
        themeMode: ThemeMode.system,
        home: const NavigationMenu());
    //home: const AppHome(),
    //home: const WelcomeScreen());
  }
}

class AppHome extends StatelessWidget {
  const AppHome({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text(".TranTrung"),
          leading: const Icon(Icons.ondemand_video),
        ),
        floatingActionButton: FloatingActionButton(
          child: const Icon(Icons.add_shopping_cart_outlined),
          onPressed: () {},
        ),
        body: Padding(
            padding: const EdgeInsets.all(20.0),
            child: ListView(
              children: [
                Text(
                  "Heading",
                  style: Theme.of(context).textTheme.displayMedium,
                ),
                Text(
                  "Sub-heading",
                  style: Theme.of(context).textTheme.titleSmall,
                ),
                Text(
                  "Sub-heading",
                  style: Theme.of(context).textTheme.bodyLarge,
                ),
                ElevatedButton(
                  onPressed: () {},
                  child: const Text("123213"),
                ),
                OutlinedButton(
                  onPressed: () {},
                  child: const Text("123213"),
                ),
                const Padding(
                    padding: EdgeInsets.all(20.0),
                    child: Image(image: AssetImage("assets/images/books.png"))),
              ],
            )));
  }
}
