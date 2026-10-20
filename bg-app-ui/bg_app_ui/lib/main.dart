import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/config/app_config.dart';
import 'package:bg_app_ui/di/dependencies.dart';
import 'package:bg_app_ui/model/backend/session_service.dart';
import 'package:bg_app_ui/model/repository/local_db.dart';
import 'package:bg_app_ui/model/repository/session/session_repository.dart';
import 'package:bg_app_ui/model/repository/user/local_user_repository.dart';
import 'package:bg_app_ui/model/tesera/tesera_service.dart';
import 'package:bg_app_ui/view/board/create_board.dart';
import 'package:bg_app_ui/view/board/invite_page.dart';
import 'package:bg_app_ui/view/board/reg_tesera_page.dart';
import 'package:bg_app_ui/view/commons/types.dart';
import 'package:bg_app_ui/view/games/games_list_page.dart';
import 'package:bg_app_ui/view/home/home_page.dart';
import 'package:bg_app_ui/view/login/login_page.dart';
import 'package:bg_app_ui/view/table/create_table_page.dart';
import 'package:bg_app_ui/view/table/join_table_page.dart';
import 'package:bg_app_ui/viewmodel/auth_service.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

// ignore: depend_on_referenced_packages
import 'package:flutter/widgets.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await LocalDb.initialize();

  AppConfig config = AppConfig.fromConfigFile();
  AppCache appCache = AppCache();
  LocalUserRepository localUserRepository = LocalUserRepository();
  SessionRepository sessionRepository = SessionRepository();
  AuthService authService = AuthService(sessionRepository: sessionRepository);

  bool isAuth = await authService.isUserAuthenticated();

  runApp(
    MultiProvider(
      providers: buildDependencies(
        config, 
        appCache,
        localUserRepository,
        sessionRepository,
        authService
      ),
      child: MyApp(isAuthenticated: isAuth),
    )
  );
}

class MyApp extends StatelessWidget {
  final bool isAuthenticated;

  const MyApp({
    super.key, 
    required this.isAuthenticated
  });

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Настолочная',
      theme: ThemeData(
        colorScheme: .fromSeed(seedColor: Colors.deepPurple),
      ),
      initialRoute: isAuthenticated ? AppRoutes.home : AppRoutes.login,
      routes: {
        AppRoutes.login: (context) => LoginPage(
          sessionRepository: context.read<SessionRepository>(),
          sessionService: context.read<SessionService>(),
        ),
        AppRoutes.home: (context) => const HomePage(),
        AppRoutes.invite: (context) => const InviteFriendPage(),
        AppRoutes.tableMenu: (context) => const BgAppCreateBoard(),
        AppRoutes.createTable: (context) => const CreateTablePage(),
        AppRoutes.joinTable: (context) => const JoinTablePage(),
        AppRoutes.gamesList: (context) => const GamesListPage(),
        AppRoutes.regTesera: (context) => RegTeseraUserPage(teseraService: context.read<TeseraService>())
      },
    );
  }
}
