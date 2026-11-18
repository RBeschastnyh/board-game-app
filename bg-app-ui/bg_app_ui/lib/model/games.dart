class Games {

  final String gameName;
  final String? gameNameEng;
  final int? teseraGameId;
  final int? userId;
  final String? image;
  final String? description;
  final double? rating;
  final int gamesPlayed;
  final int gamesWon;
  final bool isAddition; 
  final int? minPlayers;
  final int? maxPlayers;
  final int? recommendedPlayersMin;
  final int? recommendedPlayersMax;
  final int? year;

  const Games({
    required this.gameName,
    required this.gamesPlayed,
    required this.gamesWon,
    required this.isAddition,
    this.year,
    this.userId,
    this.image,
    this.gameNameEng,
    this.teseraGameId,
    this.description,
    this.minPlayers,
    this.maxPlayers,
    this.rating,
    this.recommendedPlayersMin,
    this.recommendedPlayersMax,
  });

  factory Games.fromMap(Map<String, Object?> gameObj) {
    return Games(
        teseraGameId: gameObj['tesera_game_id']! as int,
        gameName: gameObj['game_title']! as String,
        isAddition: bool.parse(gameObj['is_addition'] as String? ?? "false"),
        year: int.parse(gameObj['production_year'] as String? ?? "0"),
        rating: double.parse(gameObj['user_rating'] as String? ?? "0.0"),
        gameNameEng: gameObj['eng_game_title'] as String? ?? gameObj['game_title']! as String,
        minPlayers: int.parse(gameObj['players_min'] as String? ?? "0"),
        maxPlayers: int.parse(gameObj['players_max'] as String? ?? "0"),
        recommendedPlayersMin: int.parse(gameObj['recommended_players_min'] as String? ?? "0"),
        recommendedPlayersMax: int.parse(gameObj['recommended_players_max'] as String? ?? "0"),
        // fixme: Необходимо определять каким-то образом
        gamesPlayed: 0,
        gamesWon: 0
    );
  }

  double get winRate => gamesWon / (gamesPlayed > 0 ? gamesPlayed : 1);

  @override
  String toString() {
    return """
      {"gameName" : $gameName, "gameNameEng": $gameNameEng, "description": $description, "gamesWon": $gamesWon, "isAddition": $isAddition, "year": $year, "userId": $userId, "image": $image, "teseraGameId": $teseraGameId, "minPlayers": $minPlayers, "maxPlayers": $maxPlayers, "rating": $rating, "recommendedPlayersMin": $recommendedPlayersMin, "recommendedPlayersMax": $recommendedPlayersMax}
    """;
  }
  
}