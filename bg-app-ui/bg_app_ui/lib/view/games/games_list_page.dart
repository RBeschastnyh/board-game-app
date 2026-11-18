import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/model/games.dart';
import 'package:bg_app_ui/model/repository/games/games_repository.dart';
import 'package:bg_app_ui/model/repository/games/tesera_game.dart';
import 'package:bg_app_ui/model/repository/user/local_user.dart';
import 'package:bg_app_ui/model/tesera/tesera_service.dart';
import 'package:bg_app_ui/model/tesera_user.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:bg_app_ui/view/commons/buttons/bg_app_home_button.dart';
import 'package:bg_app_ui/view/commons/buttons/default_text_button.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

class GamesListPage extends StatefulWidget {
  const GamesListPage({
    super.key,
    required GamesRepository gamesRepository,
    required TeseraService teseraService
  }) : _gamesRepository = gamesRepository, _teseraService = teseraService;

  final GamesRepository _gamesRepository;
  final TeseraService _teseraService;

  @override
  State<StatefulWidget> createState() => _GamesListPageState();
}

class _GamesListPageState extends State<GamesListPage> {
  late List<Games> _games;
  late bool _gamesListIsEmpty = true;
  late bool _syncInProcess = false;
  late bool _allowSyncGames;
  late bool _allowUploadGames;
  
  static final Logger _logger = initLogger('_GamesListPageState');

  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    _logger.info('INIT GAMES LIST');

    TeseraUser? teseraUser = AppCache.getInstance().get<TeseraUser>("current_tesera_user");
    if (teseraUser != null) {
      _allowSyncGames = true;
      _allowUploadGames = true;
    }

    _games = [];
    _gamesListIsEmpty = _games.isEmpty;

    initGamesList();
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
      children: [
        Spacer(), 
        Text("В списке игр пока пусто :("),
        SizedBox(width: 20.0, height: 20.0,),
        _hasAnyTeseraInformation() ? DefaultTextButton(
          text: "Загрузить с Tesera",
          callback: !_syncInProcess ? _handelLoadTeseraGames : null,
        ) : SizedBox.shrink(), 
        Spacer()
      ],
    );
  }

  void _handelLoadTeseraGames() async {
    setState(() {
      _syncInProcess = true;
    });

    List<Games> list = (await widget._teseraService.findGamesByTeseraUsername())
      .map((tGame) => tGame.toGame())
      .toList();

    _logger.fine("Games acquired ${list.length}");

    LocalUser? localUser = AppCache.getInstance().get<LocalUser>("current_user")!;

    widget._gamesRepository.saveList(list, localUser.username.toLowerCase());

    setState(() {
      _games = list;
      _gamesListIsEmpty = list.isEmpty;
      _syncInProcess = false;
    });
  }

  bool _hasAnyTeseraInformation() {
    return AppCache.getInstance().get("current_tesera_user") != null || AppCache.getInstance().get<LocalUser>("current_user")?.teseraUserId != null;
  }

  void initGamesList() async {
    LocalUser? localUser = AppCache.getInstance().get<LocalUser>("current_user");

    if (localUser == null) {
      _logger.shout("SEVERE PROBLEM OCCURRED, LOCAL USER IN NULL!!!");
      return;
    }

    List<Games> list = await widget._gamesRepository.getAllGamesByUsername(localUser.username);

    setState(() {
      _games = list;
      _gamesListIsEmpty = list.isEmpty;
    });
  }
}
