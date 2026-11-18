import 'dart:async';
import 'dart:convert';
import 'dart:ffi';
import 'dart:io';

import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/exceptions/user_not_found_exception.dart';
import 'package:bg_app_ui/model/repository/games/tesera_game.dart';
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

  Future<List<TeseraGame>> findGamesByTeseraUsername() async {
    TeseraUser teseraUser = AppCache.getInstance().get<TeseraUser>("current_tesera_user")!;

    List<TeseraGame> resultList = List.empty(growable: true);

    for (int i = 0; i < 1 + teseraUser.gamesInCollection ~/ 30; ++i) {
      await Future.delayed(Duration(seconds: 3));
      late Response response;

      _logger.info(Uri.parse("$baseUrl/collections/base/own/${teseraUser.teseraId}?v=1${i > 0 ? "&offset=$i": ""}&limit=30"));

      try {
        response = await http.get(
          Uri.parse("$baseUrl/collections/base/own/${teseraUser.teseraId}?v=1${i > 0 ? "&offset=$i": ""}&limit=30"),
          headers: <String, String>{
            'Content-Type': 'application/json;charset=utf-8',
          },
        ).catchError((err) {
          return http.Response(err.toString(), 500);
        });
      } catch (e) {
        _logger.severe("Error occurred", e);
      }

      if (isHttpStatusError(response.statusCode)) {
        _logger.severe("Error occurred while acquiring games for user ${teseraUser.username}");
        break;
      }

      List<dynamic> jsonList = (jsonDecode(response.body) as List<dynamic>);

      _logger.finest("Acquired ${jsonList.length} games");

      if (jsonList.isEmpty) {
        break;
      } else {
        List<TeseraGame> list = jsonList
        .map((elem) => TeseraGame.fromJson(elem as Map<String, dynamic>))
        .toList();

        resultList.addAll(list);

        if (jsonList.length < 30) {
          break;
        }
      }
    }

    return Future.value(resultList);
  }
}
