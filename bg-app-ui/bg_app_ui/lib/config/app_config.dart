class AppConfig {

  final TeseraConfig teseraConfig;

  const AppConfig({
    required this.teseraConfig
  });

  factory AppConfig.fromConfigFile() {
    return AppConfig(
      teseraConfig: const TeseraConfig(
        url: String.fromEnvironment("TESERA_URL"), 
        timeout: int.fromEnvironment("TESERA_TIMEOUT")
      )
    );
  }
}

class TeseraConfig {
  final String url;
  final int timeout;

  const TeseraConfig({
    required this.url,
    required this.timeout
  });
}