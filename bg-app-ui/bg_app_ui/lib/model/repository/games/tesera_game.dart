import 'dart:convert';

import 'package:bg_app_ui/model/games.dart';

class TeseraGame {

  final int teseraId;
  final String title;
  final String englishTitle;
  final String? description;
  final bool isAddition;
  final double? rating;
  final int? year; 

  const TeseraGame({
    required this.teseraId,
    required this.title,
    required this.englishTitle,
    required this.isAddition,
    this.description,
    this.rating,
    this.year
  });

  factory TeseraGame.fromJson(Map<String, dynamic> jsonValue) {
    return TeseraGame(
      teseraId: jsonValue['game']?['teseraId'],
      title: jsonValue['game']?['title'], 
      englishTitle: jsonValue['game']?['title2'] ?? jsonValue['game']?['title3'] ?? jsonValue['game']?['title'], 
      isAddition: jsonValue['game']?['isAddition'],
      year: jsonValue['game']?['year'],
      rating: jsonValue['game']?['ratingUser'],
      description: jsonValue['game']?['descriptionShort'],
    );
  }

  Games toGame() {
    return Games(
      teseraGameId: this.teseraId,
      gameName: this.title, 
      gamesPlayed: 0, 
      gamesWon: 0,
      description: this.description,
      isAddition: this.isAddition
    );
  }
}