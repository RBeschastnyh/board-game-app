class AppCache {
  static AppCache? _instance;
  final Map<String, dynamic> cache;

  AppCache._(): cache = {};

  static AppCache getInstance() => _instance!;

  static void initialize() {
    _instance = AppCache._();
  }

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