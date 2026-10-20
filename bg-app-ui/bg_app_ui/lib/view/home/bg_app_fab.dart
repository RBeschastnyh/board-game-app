import 'package:bg_app_ui/utils/logger.dart';
import 'package:bg_app_ui/view/commons/types.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

class BgAppFloatingActionButton extends StatefulWidget {
  const BgAppFloatingActionButton({super.key});

  @override
  State<StatefulWidget> createState() => _BgAppFloatingActionButtonState();
}

class _BgAppFloatingActionButtonState extends State<BgAppFloatingActionButton> {
  static final Logger _logger = initLogger('_BgAppFloatingActionButtonState');

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
          onPressed: () {
            _logger.info("Создание стола");
            Navigator.pushNamed(context, AppRoutes.tableMenu);
          },
          foregroundColor: Colors.black,
          backgroundColor: Colors.cyan,
          tooltip: "Создать стол",
          child: Icon(
            Icons.table_restaurant_sharp,
          ),
        );
  }
}