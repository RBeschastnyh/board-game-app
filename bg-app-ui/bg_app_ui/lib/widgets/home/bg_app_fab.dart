import 'package:bg_app_ui/widgets/commons/types.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

class BgAppFloatingActionButton extends StatefulWidget {
  const BgAppFloatingActionButton({super.key});

  @override
  State<StatefulWidget> createState() => _BgAppFloatingActionButtonState();
}

class _BgAppFloatingActionButtonState extends State<BgAppFloatingActionButton> {

  final Logger logger = Logger('BgAppFloatingActionButton');

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
          onPressed: () {
            logger.info("Создание стола");
            Navigator.pushNamed(context, AppRoutes.tableMenu);
          },
          foregroundColor: Colors.black,
          backgroundColor: Colors.amberAccent,
          tooltip: "Создать стол",
          child: Icon(
            Icons.table_restaurant_sharp,
          ),
        );
  }
}