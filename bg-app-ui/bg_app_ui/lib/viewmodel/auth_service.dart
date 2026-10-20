import 'package:bg_app_ui/model/repository/session/session_repository.dart';

class AuthService {

  final SessionRepository _sessionRepository;

  const AuthService({
    required SessionRepository sessionRepository
  }): _sessionRepository = sessionRepository;

  Future<bool> isUserAuthenticated() async {
    String? username = await _sessionRepository.checkIfUserHasBeenAuthorized();

    return username != null;
  }
}