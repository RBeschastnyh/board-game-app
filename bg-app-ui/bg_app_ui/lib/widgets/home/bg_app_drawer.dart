import 'package:bg_app_ui/widgets/commons/types.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

class BgAppDrawer extends StatefulWidget {
  const BgAppDrawer({super.key});

  @override
  State<BgAppDrawer> createState() => _BgAppDrawer();
}

class _BgAppDrawer extends State<BgAppDrawer> {
  final Logger logger = Logger('BgAppDrawer');

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
                  logger.fine("Загрузить с Tesera");
                  Navigator.pushNamed(context, AppRoutes.regTesera);
                },
              ),
              ListTile(
                title: Text("Мои столы"),
                onTap: () {
                  logger.fine("Мои столы");
                  Navigator.pop(context);
                },
              )
            ],
          ),
        );
  }
}