class Games {

  final String _gameName;
  final String? _image;
  final String? _description;
  final int _gamesPlayed;
  final int _gamesWon;
  final int _minPlayers;
  final int _maxPlayers;
  final int _recommendedPlayers;

  const Games(
    this._gameName,
    this._image,
    this._description,
    this._gamesPlayed,
    this._gamesWon,
    this._minPlayers,
    this._maxPlayers,
    this._recommendedPlayers,
  );

  const Games.newAdded(
    String gameName,
    String image,
    String description,
    int minPlayers,
    int maxPlayers,
    int recommendedPlayers,
  ) : _gameName = gameName, _image = image, _description = description, _gamesPlayed = 0, _gamesWon = 0, _minPlayers = minPlayers, _maxPlayers = maxPlayers, _recommendedPlayers = recommendedPlayers;

  const Games.withoutImage(
    String gameName,
    String description,
    int minPlayers,
    int maxPlayers,
    int recommendedPlayers,
  ) : _gameName = gameName, _image = null, _description = description, _gamesPlayed = 0, _gamesWon = 0, _minPlayers = minPlayers, _maxPlayers = maxPlayers, _recommendedPlayers = recommendedPlayers;

  String get getName => _gameName;
  String? get getImage => _image;
  String? get getDescription => _description;
  int get getGamesPlayed => _gamesPlayed;
  int get getGamesWon => _gamesWon;
  int get getMinPlayers => _minPlayers;
  int get getMaxPlayers => _maxPlayers;
  int get getRecommendedPlayers => _recommendedPlayers;
  double get winRate => _gamesWon / _gamesPlayed;
  
}