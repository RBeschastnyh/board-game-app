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


}