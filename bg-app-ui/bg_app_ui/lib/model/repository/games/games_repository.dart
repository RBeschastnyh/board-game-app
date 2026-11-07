import 'package:bg_app_ui/model/games.dart';
import 'package:bg_app_ui/model/repository/games/local_game.dart';
import 'package:bg_app_ui/model/repository/local_db.dart';

class GamesRepository {

  final LocalDb _db;

  GamesRepository(): _db = LocalDb.getInstance;

  Future<List<LocalGame>> getAllGamesByUserTeseraLogin(String username) async {
    List<Map<String, Object?>> games = await _db.rawQuery('''
        select g.* from user_game_map m
        inner join games g on g.id = m.game_id
        inner join users u on u.id = m.user_id
        where u.login = ?
      ''', args: [username]);

    return games.map((game) => LocalGame.fromMap(game)).toList();
  }

  void saveList(List<Games> list, String username) async {
    int totalBatches = list.length < 500 ? 1 : list.length ~/ 500;

    for (int currentBatch = 0; currentBatch < totalBatches; currentBatch++) {
      List<String> gameToInsertStatement = List.empty();
      for (int i = currentBatch * 500; i < (currentBatch + 1) * 500 && i < list.length; i++) {
        Games currentGame = list[i];
        gameToInsertStatement.add(
          "insert into games table values (${currentGame.teseraGameId ?? -1}, ${currentGame.gameName}, ${currentGame.gameNameEng ?? "NULL"}, ${currentGame.rating ?? 0.0}, ${currentGame.isAddition}, ${currentGame.year ?? DateTime.now().year}, ${currentGame.minPlayers ?? 0}, ${currentGame.maxPlayers ?? 0}, ${currentGame.recommendedPlayersMin ?? 0}, ${currentGame.recommendedPlayersMax ?? 0})"
        );
      }
      await _db.rawQuery("""
        BEGIN TRANSACTION;
        ${gameToInsertStatement.join(", ")}
        COMMIT;
      """);
    }

    
    
  }
}