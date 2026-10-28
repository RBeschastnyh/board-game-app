import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/exceptions/user_not_found_exception.dart';
import 'package:bg_app_ui/model/repository/user/local_user_repository.dart';
import 'package:bg_app_ui/model/tesera_user.dart';
import 'package:bg_app_ui/utils/http_utils.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:logging/logging.dart';

class TeseraService {

  static final Logger _logger = initLogger('TeseraService');

  final String baseUrl;
  final LocalUserRepository localUserRepository;

  const TeseraService({
    required this.baseUrl,
    required this.localUserRepository
  });

  Future<TeseraUser> getTeseraUserIfExists(String teseraUserName) async {
    TeseraUser? requestedUser = AppCache.getInstance().get<TeseraUser>(teseraUserName.toLowerCase());
    if (requestedUser != null) {
      return Future.syncValue(requestedUser);
    }

    // LocalDb.getInstance.doesUserExist();

    late Response response;
    try {
      response = await http.get(
        Uri.parse("$baseUrl/user/$teseraUserName"),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=utf-8',
        },
      );
    } catch (e) {
      _logger.severe("Error occurred", e);
    }

    if (isHttpStatusServerError(response.statusCode)) {
      _logger.severe("Error occurred while fetching tesera user");

      throw HttpException("Не можем получить ответ от Tesera :(");
    }

    if (isNotFound(response.statusCode)) {
      _logger.severe("Not found user $teseraUserName on Tesera");

      throw UserNotFoundException("Пользователь с ником $teseraUserName не найден");
    }

    if (isHttpStatusError(response.statusCode)) {
      _logger.severe("Error occurred while fetching tesera user, got response with code ${response.statusCode}");

      throw HttpException("При отправке запроса возникла ошибка. Разбираемся");
    }

    TeseraUser teseraUser = AppCache.getInstance().put<TeseraUser>(teseraUserName.toLowerCase(), TeseraUser.fromJson(jsonDecode(response.body) as Map<String, dynamic>));

    return Future.syncValue(teseraUser);
  }
}
