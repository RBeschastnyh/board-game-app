import 'dart:math';

import 'package:bg_app_ui/widgets/commons/buttons/bg_app_home_button.dart';
import 'package:flutter/material.dart';

class CreateTablePage extends StatefulWidget {
  const CreateTablePage({super.key});

  @override
  State<StatefulWidget> createState() => _CreateTablePageState();
}

class _CreateTablePageState extends State<CreateTablePage> {
  late String _tableCode;

  String _getTableCode() {
    var res = "";
    var rand = Random();
    while (res.length < 5) {
      var next = rand.nextInt(43);
      if (next < 10 || next > 16) {
        res += String.fromCharCode(next + 48);
      }
    }

    return res;
  }

  @override
  void initState() {
    _tableCode = _getTableCode();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          onPressed: () => Navigator.of(context).pop(),
          icon: Icon(Icons.arrow_back, color: Colors.black),
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            Spacer(),
            Text("Покажите друзьям код"),
            SizedBox(height: 25.0),
            Text(
              _tableCode, 
              style: TextStyle(
                color: Colors.deepPurple,
                fontSize: 40.0  
              ),
            ),
            Spacer(),
            Text("или"),
            Spacer(),
            Text("Отсканируйте QR-код"),
            Spacer(),
          ],
        ),
      ),
      floatingActionButton: BgAppHomeButton(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }
}
