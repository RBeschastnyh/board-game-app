import 'package:flutter/material.dart';

class InviteFriendPage extends StatefulWidget {
  const InviteFriendPage({super.key});

  @override
  State<StatefulWidget> createState() => _InviteFriendPageState();
}

class _InviteFriendPageState extends State<InviteFriendPage> {

  final TextEditingController _textController = TextEditingController();

  bool _validEmail = true;

  @override
  void dispose() {
    _textController.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    _textController.addListener(() {

    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          onPressed: () {
            Navigator.of(context).pop();
          }, 
          icon: Icon(
            Icons.arrow_back,
            color: Colors.black,
          )
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            Spacer(),
            Text("Кого приглашаем? Введите электронную почту"),
            TextField(
              controller: _textController,
              decoration: InputDecoration(
                icon: Icon(
                  Icons.email_outlined
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.all(Radius.circular(20.0))
                ),
                errorText: !_validEmail ? "Некорректный адрес" : null
              ),
            ),
            Spacer(),
            TextButton(
              onPressed: () {
                print("отправляю приглашение!");

                Navigator.of(context).popUntil(ModalRoute.withName('/'));
              },
              style: TextButton.styleFrom(
                backgroundColor: Colors.amberAccent,
                foregroundColor: Colors.black
              ),
              child: Text("Пригласить!")
            ),
            Spacer(),
          ],
        ),
      ),
    );
  }
}