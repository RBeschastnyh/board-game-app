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
}