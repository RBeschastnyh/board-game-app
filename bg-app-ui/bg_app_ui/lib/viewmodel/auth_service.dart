import 'package:bg_app_ui/model/repository/session/session_repository.dart';
import 'package:logging/logging.dart';

import '../utils/logger.dart';

class AuthService {

  final SessionRepository _sessionRepository;

  static final Logger _logger = initLogger('AuthService');

  const AuthService({
    required SessionRepository sessionRepository
  }): _sessionRepository = sessionRepository;

  Future<bool> isUserAuthenticated() async {
    late String? username;
    try {
      username = await _sessionRepository.checkIfUserHasBeenAuthorized();
    } catch (e) {
      _logger.severe("Error occurred while searching for session", e);
    }

    return username != null;
  }
}