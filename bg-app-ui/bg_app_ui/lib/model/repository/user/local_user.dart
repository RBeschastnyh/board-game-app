class LocalUser {
  final int? id;
  final String username;
  final String teseraUsername;
  final DateTime lastVisited;

  const LocalUser({
    required this.id,
    required this.username,
    required this.teseraUsername,
    required this.lastVisited
  });

  Map<String, Object?> toMap() {
    return {
      'id': id,
      'username': username,
      'tesera_username': teseraUsername,
      'last_visited': lastVisited
    };
  }

  @override
  String toString() {
    return "LocalUser{id:$id, username:$username, tesera_username:$teseraUsername, last_visited:$lastVisited}";
  }
}