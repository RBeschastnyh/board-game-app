import 'package:bg_app_ui/home/bg_app_bottom_nav_bar.dart';
import 'package:bg_app_ui/home/bg_app_drawer.dart';
import 'package:bg_app_ui/home/bg_app_fab.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {

  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();

}

class _HomePageState extends State<HomePage> {

  @override
  Widget build(BuildContext context) {
      return Scaffold(
        appBar: AppBar(title: Text("С бутером"),),
        drawer: BgAppDrawer(),
        floatingActionButton: BgAppFloatingActionButton(),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        bottomNavigationBar: BgAppBottomNavigationBar(),
      );
  }
}