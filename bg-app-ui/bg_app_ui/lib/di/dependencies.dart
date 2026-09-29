import 'package:bg_app_ui/model/tesera/tesera_service.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> buildDependencies() {
  return [
    Provider<TeseraService>(
      create: (_) => TeseraService(),
    )
  ];
}