import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/exceptions/user_not_found_exception.dart';
import 'package:bg_app_ui/model/tesera_user.dart';
import 'package:bg_app_ui/utils/http_utils.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'package:logging/logging.dart';

class TeseraService {

  final String baseUrl;
  final AppCache appCache;

  const TeseraService({
    required this.baseUrl,
    required this.appCache
  });

  Future<TeseraUser> getTeseraUserIfExists(String teseraUserName) async {
    var logger = Logger(runtimeType.toString());

    TeseraUser? requestedUser = appCache.get<TeseraUser>(teseraUserName.toLowerCase());
    if (requestedUser != null) {
      return Future.syncValue(requestedUser);
    }

    late Response response;
    try {
      response = await http.get(
        Uri.parse("$baseUrl/user/$teseraUserName"),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=utf-8',
        },
      );
    } catch (e) {
      logger.severe("Error occurred", e);
    }

    if (isHttpStatusServerError(response.statusCode)) {
      logger.severe("Error occurred while fetching tesera user");

      throw HttpException("Не можем получить ответ от Tesera :(");
    }

    if (isNotFound(response.statusCode)) {
      logger.severe("Not found user $teseraUserName on Tesera");

      throw UserNotFoundException("Пользователь с ником $teseraUserName не найден");
    }

    if (isHttpStatusError(response.statusCode)) {
      logger.severe("Error occurred while fetching tesera user, got response with code ${response.statusCode}");

      throw HttpException("При отправке запроса возникла ошибка. Разбираемся");
    }

    TeseraUser teseraUser = appCache.put<TeseraUser>(teseraUserName.toLowerCase(), TeseraUser.fromJson(jsonDecode(response.body) as Map<String, dynamic>));

    return Future.syncValue(teseraUser);
  }
}
