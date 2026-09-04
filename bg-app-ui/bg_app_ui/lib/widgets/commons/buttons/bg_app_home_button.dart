import 'package:bg_app_ui/widgets/commons/types.dart';
import 'package:flutter/material.dart';

class BgAppHomeButton extends StatefulWidget {
  const BgAppHomeButton({super.key});

  @override
  State<StatefulWidget> createState() => _BgAppHomeButton();
}

class _BgAppHomeButton extends State<BgAppHomeButton> {
  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
        onPressed: () {
          Navigator.of(context).popUntil(ModalRoute.withName(AppRoutes.home));
        },
        backgroundColor: Colors.amberAccent,
        child: Icon(
          Icons.home,
          color: Colors.black,
        ),
      );
  }
}