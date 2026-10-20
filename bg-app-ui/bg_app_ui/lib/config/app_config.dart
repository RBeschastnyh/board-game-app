class AppConfig {

  final TeseraConfig teseraConfig;
  final BackendConfig backendConfig;

  const AppConfig({
    required this.teseraConfig,
    required this.backendConfig
  });

  factory AppConfig.fromConfigFile() {
    return AppConfig(
      teseraConfig: const TeseraConfig(
        url: String.fromEnvironment("TESERA_URL"), 
        timeout: int.fromEnvironment("TESERA_TIMEOUT")
      ),
      backendConfig: const BackendConfig(
        backendUrl: String.fromEnvironment("BACKEND_URL")
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

class BackendConfig {
  final String backendUrl;

  const BackendConfig({
    required this.backendUrl
  });
}