import 'package:bg_app_ui/exceptions/user_not_found_exception.dart';
import 'package:bg_app_ui/model/tesera/tesera_service.dart';
import 'package:bg_app_ui/model/tesera_user.dart';
import 'package:bg_app_ui/widgets/commons/buttons/bg_app_home_button.dart';
import 'package:bg_app_ui/widgets/commons/buttons/default_cancel_text_button.dart';
import 'package:bg_app_ui/widgets/commons/buttons/default_text_button.dart';
import 'package:bg_app_ui/widgets/commons/types.dart';
import 'package:flutter/material.dart';

class RegTeseraUserPage extends StatefulWidget {
  const RegTeseraUserPage({super.key, required TeseraService teseraService})
    : _teseraService = teseraService;

  final TeseraService _teseraService;

  @override
  State<StatefulWidget> createState() => _RegTeseraUserPageState();
}

class _RegTeseraUserPageState extends State<RegTeseraUserPage> {
  final TextEditingController _teseraUsenameController =
      TextEditingController();

  late FocusNode _teseraUsernameFocusNode;
  late FocusAttachment _teseraUsernameFocusAttachment;
  late String previousLoginInput;
  late String _currentTeseraUserInput;
  late TeseraUser _currentUser;

  bool _isErrorOnUsernameInput = false;

  void _handleTeseraUsernameInput() {
    _currentTeseraUserInput = _teseraUsenameController.value.text;

    _teseraUsenameController.value = _teseraUsenameController.value.copyWith(
      text: _teseraUsenameController.text.substring(
        0,
        _teseraUsenameController.text.length > 40
            ? 40
            : _teseraUsenameController.text.length,
      ),
      selection: TextSelection(
        baseOffset: _teseraUsenameController.value.text.length,
        extentOffset: _teseraUsenameController.value.text.length % 40,
      ),
    );
  }

  void _successCallback(TeseraUser user) {
    print("Найден ${user.username}");
    _currentUser = user;
    setState(() {
      _isErrorOnUsernameInput = false;
    });
  }

  void _handleTeseraUsernameFocusChanged() {
    if (!_teseraUsernameFocusNode.hasFocus &&
        _teseraUsenameController.text.isNotEmpty) {
      try {
        widget._teseraService
            .getTeseraUserIfExists(_teseraUsenameController.text)
            .then(
              (user) => _successCallback(user),
              onError: (e) {
                if (e is UserNotFoundException) {
                  setState(() {
                    _isErrorOnUsernameInput = true;
                  });
                }
              },
            );
      } catch (e) {
        print("${e.toString()}");
        setState(() {
          _isErrorOnUsernameInput = true;
        });
      }
    }
  }

  Future<String?> _saveUserAndReturnBackToHome() async {
    return showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text("Для мамкиных хакеров"),
        content: const Text(
          "Предупреждаем, что если вы ввели чужой логин, то это скажется на качестве рекомендаций",
        ),
        actions: <Widget>[
          DefaultTextButton(
            text: "Ок",
            callback: () {
              print("Сохраняем пользователя ${_currentUser.username}");
              Navigator.of(
                context,
              ).popUntil(ModalRoute.withName(AppRoutes.home));
            },
          ),
          DefaultCancelTextButton(text: "Жаль :(")
        ],
      ),
    );
  }

  @override
  void initState() {
    _teseraUsernameFocusNode = FocusNode(
      debugLabel: "_teseraUsernameFocusNode",
    );
    _teseraUsenameController.addListener(_handleTeseraUsernameInput);

    _teseraUsernameFocusNode.addListener(_handleTeseraUsernameFocusChanged);
    _teseraUsernameFocusAttachment = _teseraUsernameFocusNode.attach(context);

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(leading: BackButton()),
      body: Center(
        child: Column(
          children: [
            Spacer(),
            Spacer(),
            Padding(
              padding: EdgeInsetsGeometry.symmetric(horizontal: 40.0),
              child: TextField(
                controller: _teseraUsenameController,
                focusNode: _teseraUsernameFocusNode,
                decoration: InputDecoration(
                  hint: Text("Введите имя пользователя Tesera"),
                  hintStyle: TextStyle(color: Colors.grey),
                  errorText: _isErrorOnUsernameInput
                      ? "Не найден пользователь $_currentTeseraUserInput"
                      : null,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20.0),
                  ),
                ),
              ),
            ),
            Spacer(),
            DefaultTextButton(
              text: "Вперёд!",
              callback:
                  !_isErrorOnUsernameInput &&
                      _teseraUsenameController.text.isNotEmpty
                  ? _saveUserAndReturnBackToHome
                  : null,
            ),
            Spacer(),
          ],
        ),
      ),
      floatingActionButton: BgAppHomeButton(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }

  @override
  void dispose() {
    _teseraUsenameController.removeListener(_handleTeseraUsernameInput);
    _teseraUsernameFocusAttachment.detach();

    _teseraUsenameController.dispose();
    _teseraUsernameFocusNode.dispose();
    super.dispose();
  }
}
