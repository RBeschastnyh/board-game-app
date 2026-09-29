import 'dart:convert';
import 'dart:io';

import 'package:bg_app_ui/exceptions/user_not_found_exception.dart';
import 'package:bg_app_ui/model/tesera_user.dart';
import 'package:bg_app_ui/utils/http_utils.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';

class TeseraService {
  Future<TeseraUser> getTeseraUserIfExists(String teseraUserName) async {
    late Response response;
    try {
      response = await http.get(
        Uri.parse("https://api.tesera.ru/user/$teseraUserName"),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=utf-8',
        },
      );
    } catch (e) {
      print("Error occurred");
    }

    if (isHttpStatusServerError(response.statusCode)) {
      print("Error occurred while fetching tesera user");

      throw HttpException("Не можем получить ответ от Tesera :(");
    }

    if (isNotFound(response.statusCode)) {
      throw UserNotFoundException("Пользователь с ником $teseraUserName не найден");
    }

    if (isHttpStatusError(response.statusCode)) {
      print("Error occurred while fetching tesera user");

      throw HttpException("При отправке запроса возникла ошибка. Разбираемся");
    }

    return TeseraUser.fromJson(jsonDecode(response.body) as Map<String, dynamic>);
  }
}
