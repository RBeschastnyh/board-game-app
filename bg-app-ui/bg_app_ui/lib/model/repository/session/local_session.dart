class LocalSession {
  final int? id;
  final String sessionId;
  final String username;
  final DateTime validFrom;
  final DateTime validUntil;
  final DateTime lastLogin;

  const LocalSession({
    required this.id,
    required this.sessionId,
    required this.username,
    required this.validFrom,
    required this.validUntil,
    required this.lastLogin,
  });

  Map<String, Object?> toMap() {
    return {
      'id': id,
      'session_id': sessionId,
      'username': username,
      'valid_from': validFrom.toIso8601String(),
      'valid_until': validUntil.toIso8601String(),
      'last_login': lastLogin.toIso8601String()
    };
  }
}