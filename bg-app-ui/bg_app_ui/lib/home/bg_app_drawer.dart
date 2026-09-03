import 'package:flutter/material.dart';

class BgAppDrawer extends StatefulWidget {
  const BgAppDrawer({super.key});

  @override
  State<BgAppDrawer> createState() => _BgAppDrawer();
}

class _BgAppDrawer extends State<BgAppDrawer> {

  @override
  Widget build(BuildContext context) {
    return Drawer(
          child: ListView(
            padding: EdgeInsets.zero,
            children: [
              const DrawerHeader(
                decoration: BoxDecoration(
                  color: Colors.amberAccent
                ),
                child: Text("Менюшка")
              ),
              ListTile(
                title: Text("Загрузить с Tesera"),
                onTap: () {
                  print("Загрузить с Tesera");
                  Navigator.pop(context);
                },
              ),
              ListTile(
                title: Text("Мои столы"),
                onTap: () {
                  print("Мои столы");
                  Navigator.pop(context);
                },
              )
            ],
          ),
        );
  }
}