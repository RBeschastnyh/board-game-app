import 'dart:convert';

import 'package:bg_app_ui/config/app_config.dart';
import 'package:bg_app_ui/exceptions/data_update_exception.dart';
import 'package:bg_app_ui/utils/http_utils.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:http/http.dart' as http;
import 'package:logging/logging.dart';

class UserService {

  static final Logger _logger = initLogger('UserService');

  final AppConfig appConfig;

  const UserService({
    required this.appConfig
  });

  void getUser() {
    // Есть ли пользователь в кэше
    // Сходить в сервис за пользователем
  }

  void createUser(String email) async {
    late http.Response response;

    try {
      response = await http.post(
        Uri.parse("${appConfig.backendConfig.backendUrl}/users/create"),
        headers: {
          'Content-type': 'application/json'
        },
        body: jsonEncode({
          'email': email
        }),
        encoding: Encoding.getByName("utf-8")
      );
    } catch (e) {
      _logger.severe('Error occurred while creating user'); 
    }
    // Проверить, есть ли с такой почтой пользователь
    // Если есть, но пароль не задан, то значит логина не было и (наверное) можно заново отправить приглашение
  }

  void updateTeseraUsername(String username, String teseraUsername, int teseraUserId) async {
    late http.Response response;

    try {
      response = await http.put(
          Uri.parse("${appConfig.backendConfig.backendUrl}/users/updateTeseraUsername"),
          headers: {
            'Content-type': 'application/json'
          },
          body: jsonEncode({
            'username': username,
            'teseraUserId': teseraUserId,
            'teseraUsername': teseraUsername
          }),
          encoding: Encoding.getByName("utf-8")
      );
    } catch (e) {
      _logger.severe(e);
      _logger.severe('Error occurred while creating user');
    }

    if (!isCreated(response.statusCode)) {
      throw DataUpdateException("Возникла ошибка. Сообщите о ней");
    }
  }
}