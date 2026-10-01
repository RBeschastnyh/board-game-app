class AppCache {
  final Map<String, dynamic> cache;

  AppCache(): cache = {};

  T put<T>(String key, T value) {
    try {
      cache[key] = value;
    } catch (e) {
      print(e);
    }
    

    return value;
  }

  T? get<T>(String key) {
    return cache[key] as T?;
  }
}