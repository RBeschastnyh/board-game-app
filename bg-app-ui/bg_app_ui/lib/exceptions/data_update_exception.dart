class DataUpdateException implements Exception {
  final String message;

  const DataUpdateException(this.message);

  String get getMessage => message;
}