import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/config/app_config.dart';
import 'package:bg_app_ui/model/backend/session_service.dart';
import 'package:bg_app_ui/model/repository/session/session_repository.dart';
import 'package:bg_app_ui/model/repository/user/local_user_repository.dart';
import 'package:bg_app_ui/model/tesera/tesera_service.dart';
import 'package:bg_app_ui/viewmodel/auth_service.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> buildDependencies(
    AppConfig config, 
    AppCache appCache,
    LocalUserRepository localUserRepository,
    SessionRepository sessionRepository,
    AuthService authService
  ) {
  return [
    Provider<AppConfig>.value(
      value: config,
    ),
    Provider<AppCache>.value(
      value: appCache,
    ),
    Provider<TeseraService>(
      create: (_) => TeseraService(
        baseUrl: config.teseraConfig.url,
        appCache: appCache,
        localUserRepository: localUserRepository
      ),
    ),
    Provider<SessionService>(
      create: (_) => SessionService(
        baseUrl: config.backendConfig.backendUrl
      ),
    ),
    Provider<AuthService>.value(
      value: authService
    ),
    Provider<SessionRepository>.value(
      value: sessionRepository
    ),
    Provider<LocalUserRepository>.value(
      value: localUserRepository
    )
  ];
}