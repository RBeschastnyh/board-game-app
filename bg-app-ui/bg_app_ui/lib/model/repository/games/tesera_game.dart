class TeseraGame {

  final String title;
  final String englishTitle;
  final String? description;
  final bool isAddition;
  final double? rating;
  final int? year; 

  const TeseraGame({
    required this.title,
    required this.englishTitle,
    required this.isAddition,
    this.description,
    this.rating,
    this.year
  });

  factory TeseraGame.fromJson(Map<String, dynamic> jsonValue) {
    return TeseraGame(
      title: jsonValue['game']?['title'], 
      englishTitle: jsonValue['game']?['title2'], 
      isAddition: jsonValue['game']?['isAddition'],
      year: jsonValue['game']?['year'],
      rating: jsonValue['game']?['ratingUser'],
      description: jsonValue['game']?['descriptionShort'],
    );
  }
}