import 'dart:convert';

import 'package:bg_app_ui/model/repository/session/local_session.dart';
import 'package:bg_app_ui/utils/http_utils.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:http/http.dart' as http;
import 'package:logging/logging.dart';

class SessionService {

  static final Logger _logger = initLogger('SessionService');

  final String baseUrl;

  SessionService({
    required this.baseUrl,
  });

  Future<bool> validateSession(String sessionId, String login) async {
    late http.Response response; 
    
    try {
      response = await http.put(
        Uri.parse("$baseUrl/session/$sessionId/validate"),
        body: login
      );
    } catch (e) {
      _logger.warning("Error occurred while requesting session validation");

      rethrow;
    }

    if (isOk(response.statusCode)) {
      _logger.fine("Response recieved");

      return true;
    }

    return false;
  }

  Future<LocalSession?> auth(String currentLogin, String currentPassword) async {
    late http.Response response;

    _logger.info("start auth on $baseUrl/session/auth");

    try {
      response = await http.post(
        Uri.parse("$baseUrl/session/auth"),
        headers: {
          'Content-type': 'application/json;charset=utf-8'
        },
        body: jsonEncode({
          "login": currentLogin.toLowerCase(),
          "password": currentPassword
        })
      );
    } catch (e) {
      _logger.info("Error occurred: ${e.toString()}");
    }

    _logger.info("status ${response.statusCode}");

    if (isOk(response.statusCode)) {
      var body = jsonDecode(response.body);
      return LocalSession(id: body['id'], sessionId: body['session_id'], username: body['login'], validFrom: body['valid_from'], validUntil: body['valid_until']);
    }

    return null;
  }
}