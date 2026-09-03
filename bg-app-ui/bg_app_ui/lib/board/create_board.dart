import 'package:flutter/material.dart';

class BgAppCreateBoard extends StatefulWidget {
  const BgAppCreateBoard({super.key});

  @override
  State<StatefulWidget> createState() => _BgAppCreateBoard();
}

class _BgAppCreateBoard extends State<BgAppCreateBoard> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.of(context).pop(),
        ),
      ),
      body: Center(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            Container(
              decoration: BoxDecoration(
                color: Colors.amberAccent,
                borderRadius: BorderRadiusGeometry.all(Radius.circular(15.0)),
              ),
              child: IconButton(
                onPressed: () {
                  print("Создать стол");
                },
                icon: Icon(Icons.table_restaurant_sharp),
                tooltip: "Создать новый стол",
                iconSize: 130.0,
              ),
            ),
            Container(
              decoration: BoxDecoration(
                color: Colors.amberAccent,
                borderRadius: BorderRadiusGeometry.all(Radius.circular(15.0)),
              ),
              child: IconButton(
                onPressed: () {
                  print("Присоединиться");
                },
                icon: Icon(Icons.hail_rounded),
                tooltip: "Присоединиться к столу",
                iconSize: 130.0,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
