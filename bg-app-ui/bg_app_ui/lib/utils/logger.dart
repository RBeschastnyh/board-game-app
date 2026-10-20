import 'dart:io';

import 'package:logging/logging.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:developer' as developer;

import 'package:flutter/foundation.dart' show kIsWeb;

Logger initLogger(String serviceName) {
  hierarchicalLoggingEnabled = true;

  final logger = Logger(serviceName);

  if (!kIsWeb) {
    getApplicationDocumentsDirectory().then((projectDir) {
      final logDirectory = Directory("${projectDir.path}/logs");
      if (!logDirectory.existsSync()) {
        logDirectory.createSync();
      }

      final now = DateTime.now();

      final logFile = File(
        '${logDirectory.path}/${now.year}_${now.month}_${now.day}_$serviceName.log',
      );

      logger.onRecord.listen((record) {
        final strFormat =
            "[${record.time} - ${record.loggerName}] ${record.level.name}: ${record.message}";
        print("[${record.time} - ${record.loggerName}] ${record.level.name}: ${record.message}");
        logFile.writeAsStringSync("$strFormat \n", mode: FileMode.append);
      });
    });
  } else {
    logger.onRecord.listen((record) {
      print("[${record.time} - ${record.loggerName}] ${record.level.name}: ${record.message}");
      developer.log(
        "[${record.time} - ${record.loggerName}] ${record.level.name}: ${record.message}\n",
        name: serviceName
      );
    });
  }

  logger.level = Level.ALL;

  return logger;
}
