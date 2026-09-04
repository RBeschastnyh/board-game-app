import 'package:bg_app_ui/widgets/commons/types.dart';
import 'package:flutter/material.dart';

class InviteFriendPage extends StatefulWidget {
  const InviteFriendPage({super.key});

  @override
  State<StatefulWidget> createState() => _InviteFriendPageState();
}

class _InviteFriendPageState extends State<InviteFriendPage> {
  final TextEditingController _textController = TextEditingController();

  late FocusAttachment _focusAttachment;
  late FocusNode _emailFocusNode;
  bool _emailFocused = false;

  bool _validEmail = false;

  void _handleEmailInputListener() {}

  void _handleEmailFocusChanged() {
    if (_emailFocusNode.hasFocus != _emailFocused) {
      setState(() {
        _emailFocused = _emailFocusNode.hasFocus;
      });
    }

    if (!_emailFocused) {
      _validEmail = RegExp(r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$').hasMatch(_textController.text);
    }
  }

  void _sendEmailInvireAndReturn() {
    print("отправляю приглашение!");
    Navigator.of(context).popUntil(ModalRoute.withName(AppRoutes.home));
  }

  @override
  void initState() {
    super.initState();

    _textController.addListener(_handleEmailInputListener);

    _emailFocusNode = FocusNode(debugLabel: "email focus node");
    _emailFocusNode.addListener(_handleEmailFocusChanged);

    _focusAttachment = _emailFocusNode.attach(
      context
    );
  }

  @override
  Widget build(BuildContext context) {
    _focusAttachment.reparent();

    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          icon: Icon(Icons.arrow_back, color: Colors.black),
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            _emailFocusNode.hasFocus ? Text("data") : Text("No focus"),
            Spacer(),
            Text("Кого приглашаем? Введите электронную почту"),
            SizedBox(width: 20.0, height: 20.0),
            Padding(
              padding: EdgeInsetsGeometry.symmetric(horizontal: 45.0),
              child: TextField(
                controller: _textController,
                focusNode: _emailFocusNode,
                keyboardType: TextInputType.emailAddress,
                decoration: InputDecoration(
                  // icon: Icon(
                  //   Icons.email_outlined
                  // ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(20.0)),
                  ),
                  errorText: _textController.text.isNotEmpty && !_validEmail ? "Некорректный адрес" : null,
                ),
              ),
            ),
            Spacer(),
            TextButton(
              onPressed: _validEmail ? _sendEmailInvireAndReturn : null,
              style: TextButton.styleFrom(
                backgroundColor: Colors.amberAccent,
                foregroundColor: Colors.black,
              ),
              child: Text("Пригласить!"),
            ),
            Spacer(),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    _textController.removeListener(_handleEmailInputListener);
    _emailFocusNode.removeListener(_handleEmailFocusChanged);
    _focusAttachment.detach();

    _textController.dispose();
    _emailFocusNode.dispose();
    super.dispose();
  }
}
