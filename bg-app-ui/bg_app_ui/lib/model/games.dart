class Games {

  final String gameName;
  final String? image;
  final String? description;
  final int gamesPlayed;
  final int gamesWon;
  final int? minPlayers;
  final int? maxPlayers;
  final int? recommendedPlayersMin;
  final int? recommendedPlayersMax;

  const Games({
    required this.gameName,
    required this.gamesPlayed,
    required this.gamesWon,
    this.image,
    this.description,
    this.minPlayers,
    this.maxPlayers,
    this.recommendedPlayersMin,
    this.recommendedPlayersMax,
  });

  // const Games.newAdded(
  //   String gameName,
  //   String image,
  //   String description,
  //   int minPlayers,
  //   int maxPlayers,
  //   int recommendedPlayers,
  // ) : _gameName = gameName, _image = image, _description = description, _gamesPlayed = 0, _gamesWon = 0, _minPlayers = minPlayers, _maxPlayers = maxPlayers, _recommendedPlayers = recommendedPlayers;

  // const Games.withoutImage(
  //   String gameName,
  //   String description,
  //   int minPlayers,
  //   int maxPlayers,
  //   int recommendedPlayers,
  // ) : _gameName = gameName, _image = null, _description = description, _gamesPlayed = 0, _gamesWon = 0, _minPlayers = minPlayers, _maxPlayers = maxPlayers, _recommendedPlayers = recommendedPlayers;

  // String get getName => _gameName;
  // String? get getImage => _image;
  // String? get getDescription => _description;
  // int get getGamesPlayed => _gamesPlayed;
  // int get getGamesWon => _gamesWon;
  // int get getMinPlayers => _minPlayers;
  // int get getMaxPlayers => _maxPlayers;
  // int get getRecommendedPlayers => _recommendedPlayers;
  double get winRate => gamesWon / (gamesPlayed > 0 ? gamesPlayed : 1);
  
}