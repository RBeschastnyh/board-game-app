class LocalUser {
  final int? id;
  final String username;
  final int? teseraUserId;
  final String? teseraUsername;
  final DateTime lastVisited;

  const LocalUser({
    required this.username,
    required this.lastVisited,
    this.id,
    this.teseraUserId,
    this.teseraUsername,
  });

  factory LocalUser.fromMap(Map<String, Object?> json) {
    var {
      'id': id as int,
      'username': username as String,
      'tesera_username': teseraUsername as String?,
      'tesera_user_id': teseraUserId as int,
      'last_visited': lastVisited as String,
    } = json;

    return LocalUser(
      username: username,
      lastVisited: DateTime.parse(lastVisited),
      teseraUserId: teseraUserId,
      teseraUsername: teseraUsername,
      id: id,
    );
  }

  Map<String, Object?> toMap() {
    return {
      'id': id,
      'username': username.toLowerCase(),
      'tesera_user_id': teseraUserId,
      'tesera_username': teseraUsername?.toLowerCase(),
      'last_visited': lastVisited.toIso8601String(),
    };
  }

  @override
  String toString() {
    return "LocalUser{id:$id, username:$username, tesera_user_id:$teseraUserId, tesera_username:$teseraUsername, last_visited:$lastVisited}";
  }
}
