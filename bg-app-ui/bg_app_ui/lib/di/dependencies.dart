import 'package:bg_app_ui/cache/internal_cache.dart';
import 'package:bg_app_ui/config/app_config.dart';
import 'package:bg_app_ui/model/tesera/tesera_service.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> buildDependencies(AppConfig config, AppCache appCache) {
  return [
    Provider<AppConfig>.value(
      value: config,
    ),
    Provider<AppCache>.value(
      value: appCache,
    ),
    Provider<TeseraService>(
      create: (_) => TeseraService(
        baseUrl: config.teseraConfig.url,
        appCache: appCache
      ),
    )
  ];
}