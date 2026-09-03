import 'package:bg_app_ui/board/create_board.dart';
import 'package:flutter/material.dart';

class BgAppFloatingActionButton extends StatefulWidget {
  const BgAppFloatingActionButton({super.key});

  @override
  State<StatefulWidget> createState() => _BgAppFloatingActionButtonState();
}

class _BgAppFloatingActionButtonState extends State<BgAppFloatingActionButton> {

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
          onPressed: () {
            print("Создание стола");
            Navigator.push(
              context, 
              MaterialPageRoute<void>(
                builder: (context) => BgAppCreateBoard(),
              )
            );
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