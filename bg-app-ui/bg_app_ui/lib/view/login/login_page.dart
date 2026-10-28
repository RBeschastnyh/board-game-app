import 'dart:convert';

import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/model/backend/session_service.dart';
import 'package:bg_app_ui/model/repository/session/local_session.dart';
import 'package:bg_app_ui/model/repository/session/session_repository.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:bg_app_ui/view/commons/buttons/default_text_button.dart';
import 'package:bg_app_ui/view/commons/types.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';
import 'package:path/path.dart';
import 'package:crypto/crypto.dart';

class LoginPage extends StatefulWidget {
  final SessionRepository sessionRepository;
  final SessionService sessionService;

  const LoginPage({
    super.key,
    required this.sessionRepository,
    required this.sessionService
  });

  @override
  State<StatefulWidget> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  static final Logger _logger = initLogger('_LoginPageState');

  final TextEditingController _loginTextController = TextEditingController();
  final TextEditingController _passwordTextController = TextEditingController();
  String? _currentLogin;
  String? _currentPassword;
  String? _errorOnLoginText;

  bool _validLogin = false;
  bool _validPassword = true;
  bool _showPasswordBlock = false;
  bool _success = false;

  void _handleLoginInput() {
    String currentText = _loginTextController.value.text;
    _currentLogin = currentText.substring(0, currentText.length < 30 ? currentText.length : 30);
    
    setState(() {
      _validLogin = _currentLogin != null && _currentLogin!.isNotEmpty;
      _errorOnLoginText = null;
    });

    _loginTextController.value = _loginTextController.value.copyWith(
      text: _currentLogin,
    );
  }

  void _handlePasswordInput() {
    String currentText = _passwordTextController.value.text;

    setState(() {
      _validPassword = currentText.isNotEmpty && RegExp(r'^\w{8,50}$').hasMatch(currentText);
    });

    _currentPassword = sha256.convert(utf8.encode(currentText)).toString();
    _logger.fine("======= $_currentPassword");
    
  }

  Future<bool> _handleLoginCallback() async {
    bool everAuth = await widget.sessionRepository
      .checkCurrentLoginEverAuthenticated(_currentLogin!);

    _logger.fine('ever auth: $everAuth');

    if (everAuth) {
      String? sessionId = await widget.sessionRepository.getLastSessionIdForUser(_currentLogin!);

      if (sessionId == null) {
        _logger.info("Requested session for user $_currentLogin which has not one");

        setState(() {
          _errorOnLoginText = 'Необходим вход';
        });

        return false;
      }

      _logger.fine('found session: $sessionId');
      widget.sessionService.validateSession(sessionId, _currentLogin!);
      widget.sessionRepository.updateSessionFor(_currentLogin!);

      AppCache.getInstance().put("current_user", _currentLogin!);

      return true;
    } else {
      if (_currentPassword == null) {
        setState(() {
          _errorOnLoginText = 'Необходим вход';
        });

        return false;
      }

      LocalSession? session = await widget.sessionService.auth(_currentLogin!, _currentPassword!);

      widget.sessionRepository.save(session);

      AppCache.getInstance().put("current_user", _currentLogin!);

      return true;
    }
  }

  @override
  void initState() {
    super.initState();

    _loginTextController.addListener(_handleLoginInput);
    _passwordTextController.addListener(_handlePasswordInput);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Spacer(),
            Text('Добро пожаловать в Настолочную!'),
            SizedBox(width: 20.0, height: 20.0),
            Padding(
              padding: EdgeInsetsGeometry.symmetric(horizontal: 45.0),
              child: TextField(
                controller: _loginTextController,
                keyboardType: TextInputType.text,
                decoration: InputDecoration(
                  hintText: "Логин",
                  hintStyle: TextStyle(
                    color: Colors.grey[400]
                  ),
                  errorText: _errorOnLoginText,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(20.0)),
                  ),
                ),
              ),
            ),
            _showPasswordBlock ? SizedBox(width: 20.0, height: 20.0) : SizedBox.shrink(),
            _showPasswordBlock ? Padding(
              padding: EdgeInsetsGeometry.symmetric(horizontal: 45.0),
              child: TextField(
                obscureText: true,
                autocorrect: false,
                enableSuggestions: false,
                controller: _passwordTextController,
                keyboardType: TextInputType.text,
                decoration: InputDecoration(
                  // suffixIcon: Container(),
                  hintText: "Пароль",
                  hintStyle: TextStyle(
                    color: Colors.grey[400]
                  ),
                  errorText: _errorOnLoginText,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(20.0)),
                  ),
                ),
              ),
            ) : SizedBox.shrink(),
            SizedBox(width: 20.0, height: 20.0),
            Text("или"),
            DefaultTextButton(
              text: !_showPasswordBlock ? 'Если в первый раз' : 'Если не в первый раз', 
              callback: () => setState(() {
                _showPasswordBlock = !_showPasswordBlock;
                _currentPassword = null;
                _passwordTextController.clear();
              })
            ),
            Spacer(),
            DefaultTextButton(
              text: 'Вперёд!', 
              callback: _validLogin && _validPassword ? () async {
                if (await _handleLoginCallback()) {
                  Navigator.pushNamed(context, AppRoutes.home);
                }
              } : null
            ),
            SizedBox(width: 20.0, height: 60.0),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    // TODO: implement dispose
    _loginTextController.removeListener(_handleLoginInput);
    _loginTextController.dispose();
    super.dispose();
  }
}
