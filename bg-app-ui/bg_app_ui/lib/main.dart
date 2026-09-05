import 'package:bg_app_ui/widgets/board/create_board.dart';
import 'package:bg_app_ui/widgets/board/invite_page.dart';
import 'package:bg_app_ui/widgets/board/reg_tesera_page.dart';
import 'package:bg_app_ui/widgets/commons/types.dart';
import 'package:bg_app_ui/widgets/games/games_list_page.dart';
import 'package:bg_app_ui/widgets/home/home_page.dart';
import 'package:bg_app_ui/widgets/table/create_table_page.dart';
import 'package:bg_app_ui/widgets/table/join_table_page.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Настолочная',
      theme: ThemeData(
        colorScheme: .fromSeed(seedColor: Colors.deepPurple),
      ),
      initialRoute: AppRoutes.home,
      routes: {
        AppRoutes.home: (context) => const HomePage(),
        AppRoutes.invite: (context) => const InviteFriendPage(),
        AppRoutes.tableMenu: (context) => const BgAppCreateBoard(),
        AppRoutes.createTable: (context) => const CreateTablePage(),
        AppRoutes.joinTable: (context) => const JoinTablePage(),
        AppRoutes.gamesList: (context) => const GamesListPage(),
        AppRoutes.regTesera: (context) => const RegTeseraUserPage()
      },
    );
  }
}
