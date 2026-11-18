class SevereErrorException implements Exception {
  final String message;

  const SevereErrorException(this.message);

  String get getMessage => message;
}