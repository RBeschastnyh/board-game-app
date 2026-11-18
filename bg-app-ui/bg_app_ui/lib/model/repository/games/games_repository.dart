import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/exceptions/severe_error_exception.dart';
import 'package:bg_app_ui/model/games.dart';
import 'package:bg_app_ui/model/repository/games/local_game.dart';
import 'package:bg_app_ui/model/repository/local_db.dart';
import 'package:bg_app_ui/model/repository/user/local_user.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:logging/logging.dart';
import 'package:sqflite/sqflite.dart';

class GamesRepository {

  static final Logger _logger = initLogger('GamesRepository');

  final LocalDb _db;

  GamesRepository(): _db = LocalDb.getInstance;

  Future<List<Games>> getAllGamesByUsername(String username) async {
    List<Map<String, Object?>> games = await _db.rawQuery('''
        select g.* from user_game_map m
        inner join games g on g.id = m.game_id
        inner join users u on u.id = m.user_id
        where u.username = ?
      ''', args: [username.toLowerCase()]);

    _logger.info("------------------- ${games.length}");

    return games.map((game) => Games.fromMap(game)).toList();
  }

  void saveList(List<Games> list, String username) async {
    int totalBatches = list.length < 500 ? 1 : list.length ~/ 500;

    for (int currentBatch = 0; currentBatch < totalBatches; currentBatch++) {
      Batch batch = await _db.batch();
      for (int i = currentBatch * 500; i < (currentBatch + 1) * 500 && i < list.length; i++) {
        Games currentGame = list[i];
        batch.insert('games', {
          'tesera_game_id': currentGame.teseraGameId,
          'game_title': currentGame.gameName,
          'eng_game_title': currentGame.gameNameEng,
          'user_rating': currentGame.rating,
          'is_addition': currentGame.isAddition.toString(),
          'production_year': currentGame.year,
          'players_min': currentGame.minPlayers,
          'players_max': currentGame.maxPlayers,
          'recommended_players_min': currentGame.recommendedPlayersMin,
          'recommended_players_max': currentGame.recommendedPlayersMax
        });
      }

      List<Object?> result = await batch.commit(
        continueOnError: false
      );

      LocalUser? localUser = AppCache.getInstance().get<LocalUser>("current_user");

      if (localUser == null) {
        _logger.shout('NO LOCAL USER!!!');
        throw SevereErrorException('Возникла ошибка! Сообщите о ней');
      }

      batch = await _db.batch();
      for (int i = 0; i < result.length; ++i) {
        batch.insert('user_game_map', {
          'user_id': localUser.id,
          'game_id': result[i] as int
        });
      }
      batch.commit(continueOnError: false);
    }
  }
}