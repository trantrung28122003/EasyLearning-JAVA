import 'package:flutter/material.dart';
import 'package:flutter_application_elearning/src/features/shop/screens/home/home.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class NavigationMenu extends StatelessWidget {
  const NavigationMenu({super.key});

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(NavigationController());

    return Scaffold(
      bottomNavigationBar: Obx(
        () => NavigationBar(
          height: 70,
          elevation: 1,
          backgroundColor: Colors.white,
          selectedIndex: controller.selectedIndex.value,
          onDestinationSelected: (index) {
            print('Selected index: $index'); // Debug
            controller.selectedIndex.value = index;
          },
          // Debug
          destinations: const [
            NavigationDestination(icon: Icon(Iconsax.home), label: 'Trang Chủ'),
            NavigationDestination(
                icon: Icon(Iconsax.search_normal), label: 'Tìm Kiếm'),
            NavigationDestination(icon: Icon(Iconsax.note), label: 'Học Tập'),
            NavigationDestination(
                icon: Icon(Iconsax.heart), label: 'Mong Muốn'),
            NavigationDestination(icon: Icon(Iconsax.user), label: 'Tài Khoản'),
          ],
        ),
      ),
      body: Obx(() => controller.screens[controller.selectedIndex.value]),
    );
  }
}

class NavigationController extends GetxController {
  final Rx<int> selectedIndex = 0.obs;

  final screens = [
    Container(
      child: const HomeScreen(),
    ),
    Container(color: Colors.purple),
    Container(color: Colors.orange),
    Container(color: Colors.blue),
    Container(color: Colors.yellow),
  ];
}
