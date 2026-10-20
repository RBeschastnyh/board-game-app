class LocalSession {
  final int? id;
  final String sessionId;
  final String username;
  final DateTime validFrom;
  final DateTime validUntil;

  const LocalSession({
    required this.id,
    required this.sessionId,
    required this.username,
    required this.validFrom,
    required this.validUntil,
  });

  Map<String, Object?> toMap() {
    return {
      'id': id,
      'session_id': sessionId,
      'username': username,
      'valid_from': validFrom,
      'valid_until': validUntil
    };
  }
}