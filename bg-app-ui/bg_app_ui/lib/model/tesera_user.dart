import 'dart:convert';

class TeseraUser {

  final String _username;
  final int _gamesInCollection;
  final int _teseraId;
  final String? _possibleName;

  String get username => _username;
  int get gamesInCollection => _gamesInCollection;
  int get teseraId => _teseraId;
  String? get possibleName => _possibleName;

  const TeseraUser({
    required int teseraId,
    required String username,
    required String? possibleName,
    required int gamesInCollection 
  }) : _teseraId = teseraId, _username = username, _gamesInCollection = gamesInCollection, _possibleName = possibleName;

  factory TeseraUser.fromJson(Map<String, dynamic> json) {
    var {'user': { 'teseraId': int? teseraId, 'login': String? login, 'name': String? possibleName }, 'gamesInCollection': int? gamesCount} = json;

    if (login == null || teseraId == null) {
      print("Error occurred, unprocessable json $jsonEncode($json)");
      throw FormatException("Ошибка при попытке получить пользователя по логину");
    }

    return TeseraUser(
        teseraId: teseraId, 
        username: login, 
        possibleName: possibleName,
        gamesInCollection: gamesCount ?? 0
      );
  }
}