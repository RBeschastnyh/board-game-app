import 'package:bg_app_ui/widgets/commons/buttons/default_cancel_text_button.dart';
import 'package:bg_app_ui/widgets/commons/buttons/default_text_button.dart';
import 'package:bg_app_ui/widgets/commons/types.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

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
      _validEmail = RegExp(
        r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$',
      ).hasMatch(_textController.text);
    }
  }

  Future<String?> _sendEmailInvireAndReturn() async {
    var logger = Logger(runtimeType.toString());
    
    return showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text("Предупреждение"),
        content: Text(
          "Убедитесь, что адрес указан правильно. Если вы указали какую-то галиматью или планируете устроить спам, то разлогитньтесь и валите нахер, мы постараемся забанить Вас как можно скорее",
        ),
        actions: <Widget>[
          DefaultTextButton(
            text: "Понятно",
            callback: () {
              logger.info("Отправляю приглашение для ${_textController.text}!");
              Navigator.of(
                context,
              ).popUntil(ModalRoute.withName(AppRoutes.home));
            },
          ),
          DefaultCancelTextButton(text: "Понял(а)")
        ],
      ),
    );
  }

  @override
  void initState() {
    super.initState();

    _textController.addListener(_handleEmailInputListener);

    _emailFocusNode = FocusNode(debugLabel: "email focus node");
    _emailFocusNode.addListener(_handleEmailFocusChanged);

    _focusAttachment = _emailFocusNode.attach(context);
  }

  @override
  Widget build(BuildContext context) {
    _focusAttachment.reparent();

    return Scaffold(
      appBar: AppBar(leading: BackButton()),
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
                  errorText: _textController.text.isNotEmpty && !_validEmail
                      ? "Некорректный адрес"
                      : null,
                ),
              ),
            ),
            Spacer(),
            DefaultTextButton(
              text: "Пригласить!",
              callback: _validEmail ? _sendEmailInvireAndReturn : null,
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
