import 'package:bg_app_ui/widgets/commons/types.dart';
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
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            Spacer(),
            Container(
              decoration: BoxDecoration(
                color: Colors.amberAccent,
                borderRadius: BorderRadiusGeometry.all(Radius.circular(15.0)),
              ),
              child: IconButton(
                onPressed: () {
                  print("Создать стол");
                  Navigator.pushNamed(context, AppRoutes.createTable);
                },
                icon: Icon(Icons.table_restaurant_sharp),
                tooltip: "Создать новый стол",
                iconSize: 130.0,
              ),
            ),
            SizedBox(height: 20.0),
            Text("Создать стол"),
            Spacer(),
            Container(
              decoration: BoxDecoration(
                color: Colors.amberAccent,
                borderRadius: BorderRadiusGeometry.all(Radius.circular(15.0)),
              ),
              child: IconButton(
                onPressed: () {
                  print("Присоединиться");
                  Navigator.pushNamed(context, AppRoutes.joinTable);
                },
                icon: Icon(Icons.hail_rounded),
                tooltip: "Присоединиться к столу",
                iconSize: 130.0,
              ),
            ),
            SizedBox(height: 20.0),
            Text("Присоединиться"),
            Spacer(),
          ],
        ),
      ),
    );
  }
}
