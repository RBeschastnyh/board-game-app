import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/model/repository/games/games_repository.dart';
import 'package:bg_app_ui/utils/logger.dart';

class GamesService {

  static final _logger = initLogger('GamesService');

  final GamesRepository gamesRepository;
  final AppCache appCache;

  const GamesService({
    required this.gamesRepository,
    required this.appCache
  });

  void getGamesFromTesera() {
    
  }

  void getUsersGames() {

  }

  void addGame() {

  }

  void deleteGame() {

  }

  void editGameInformation() {
    
  }
}