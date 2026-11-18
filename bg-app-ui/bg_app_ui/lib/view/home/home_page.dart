import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/model/repository/user/local_user.dart';
import 'package:bg_app_ui/view/home/bg_app_bottom_nav_bar.dart';
import 'package:bg_app_ui/view/home/bg_app_drawer.dart';
import 'package:bg_app_ui/view/home/bg_app_fab.dart';
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
        appBar: AppBar(title: Text("${AppCache.getInstance().get<LocalUser?>("current_user")?.username}, моё почтение!"),),
        drawer: BgAppDrawer(),
        floatingActionButton: BgAppFloatingActionButton(),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        bottomNavigationBar: BgAppBottomNavigationBar(),
      );
  }
}