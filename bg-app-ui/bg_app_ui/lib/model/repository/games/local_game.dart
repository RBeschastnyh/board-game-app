class LocalGame {
  final int? id;
  final int teseraGameId;
  final String gameTitle;
  final String? englishTitle;
  final double? userRating;
  final bool isAddition;
  final int productionYear;
  final int? playersMin;
  final int? playersMax;
  final int? recommendedPlayersMin;
  final int? recommendedPlayersMax;

  const LocalGame({
    required this.teseraGameId,
    required this.gameTitle,
    required this.isAddition,
    required this.productionYear,
    this.id,
    this.userRating,
    this.englishTitle,
    this.playersMin,
    this.playersMax,
    this.recommendedPlayersMin,
    this.recommendedPlayersMax,
  });

  factory LocalGame.fromMap(Map<String, Object?> gameObj) {
    return LocalGame(
      teseraGameId: gameObj['tesera_game_id']! as int, 
      gameTitle: gameObj['game_title']! as String, 
      isAddition: gameObj['game_title']! as bool, 
      productionYear: gameObj['game_title']! as int,
      userRating: gameObj['user_rating'] as double?,
      englishTitle: gameObj['eng_game_title'] as String?,
      playersMin: gameObj['players_min'] as int?,
      playersMax: gameObj['players_max'] as int?,
      recommendedPlayersMin: gameObj['recommended_players_min'] as int?,
      recommendedPlayersMax: gameObj['recommended_players_max'] as int?
    );
  }
}