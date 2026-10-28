import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/model/games.dart';
import 'package:bg_app_ui/model/repository/games/games_repository.dart';
import 'package:bg_app_ui/model/tesera_user.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:bg_app_ui/view/commons/buttons/bg_app_home_button.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

class GamesListPage extends StatefulWidget {
  const GamesListPage({
    super.key,
    required GamesRepository gamesRepository
  }) : _gamesRepository = gamesRepository;

  final GamesRepository _gamesRepository;

  @override
  State<StatefulWidget> createState() => _GamesListPageState();
}

class _GamesListPageState extends State<GamesListPage> {
  late List<Games> _games;
  late bool _gamesListIsEmpty = true;
  late bool _allowSyncGames;
  late bool _allowUploadGames;
  
  static final Logger _logger = initLogger('_GamesListPageState');

  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    TeseraUser? teseraUser = AppCache.getInstance().get<TeseraUser>("current_tesera_user");
    if (teseraUser != null) {
      _allowSyncGames = true;
      _allowUploadGames = true;
    } else {
      
    }

    _games = [
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
      // Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4)
    ];

    _gamesListIsEmpty = _games.isEmpty;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(leading: BackButton()),
      body: Center(
        child: _gamesListIsEmpty ? _getEmptyListBody() : _createGamesListView(),
      ),
      floatingActionButton: BgAppHomeButton(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }

  Widget _createGamesListView() {
    return ListView.separated(
      separatorBuilder: (context, index) => SizedBox(height: 15.0),
      itemCount: _games.length,
      itemBuilder: (context, index) {
        return ListTile(
          shape: BoxBorder.fromLTRB(top: BorderSide(color: Colors.black, style: BorderStyle.solid)),
          onLongPress: () => _logger.fine("Меня долго зажали"),
          leading: Image.asset(
            "assets/images/noun_Meeple_128.png",
            width: 128.0,
            height: 128.0,
          ),
          title: Row(
            children: [
              Column(
                children: [
                  Text(_games[index].description ?? ""),
                  Row(
                    children: [
                      Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Text("Сыграно ${_games[index].gamesPlayed}"),
                          Text("Побед ${_games[index].gamesWon}"),
                          Text("Win-rate ${_games[index].winRate}"),
                        ],
                      ),
                      TextButton(
                        onPressed: () => _logger.fine("Жмяк"),
                        child: Text("Придумать"),
                      ),
                    ],
                  ),
                ],
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _getEmptyListBody() {
    return Column(
      children: [Spacer(), Text("В списке игр пока пусто :("), Spacer()],
    );
  }
}
