class Games {

  final String gameName;
  final String? gameNameEng;
  final int? teseraGameId;
  final int? userId;
  final String? image;
  final String? description;
  final int? rating;
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

  double get winRate => gamesWon / (gamesPlayed > 0 ? gamesPlayed : 1);

  @override
  String toString() {
    return """
      {"gameName" : $gameName, "gameNameEng": $gameNameEng, "description": $description, "gamesWon": $gamesWon, "isAddition": $isAddition, "year": $year, "userId": $userId, "image": $image, "teseraGameId": $teseraGameId, "minPlayers": $minPlayers, "maxPlayers": $maxPlayers, "rating": $rating, "recommendedPlayersMin": $recommendedPlayersMin, "recommendedPlayersMax": $recommendedPlayersMax}
    """;
  }
  
}