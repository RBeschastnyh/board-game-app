import 'package:bg_app_ui/board/invite_page.dart';
import 'package:flutter/material.dart';

typedef OneArgVoidFunction = void Function(BuildContext build);

class BgAppBottomNavigationBar extends StatefulWidget {
  const BgAppBottomNavigationBar({super.key});

  @override
  State<StatefulWidget> createState() => _BgAppBottomNavigationBar();
}

class _BgAppBottomNavigationBar extends State<BgAppBottomNavigationBar> {
  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      backgroundColor: Colors.amberAccent,
      onTap: (index) {
        switch (index) {
          case 2:
            Navigator.push(
              context,
              MaterialPageRoute<void>(builder: (context) => InviteFriendPage()),
            );
          default: print("Выбрано что-то в менюшке снизу");
        }
      },
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Icon(Icons.groups_2, color: Colors.black),
          label: "Друзья",
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.shelves, color: Colors.black),
          label: "Игры",
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.group_add, color: Colors.black),
          label: "Пригласить",
        ),
      ],
    );
  }
}
