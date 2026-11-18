class UserNotFoundException implements Exception {
  final String message;

  const UserNotFoundException(this.message);

  String get getMessage => message;
}