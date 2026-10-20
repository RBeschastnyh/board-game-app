import 'package:bg_app_ui/model/repository/local_db.dart';
import 'package:bg_app_ui/model/repository/user/local_user.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:logging/logging.dart';

class LocalUserRepository {
  static final Logger _logger = initLogger('LocalUserRepository');

  final LocalDb _localDb;

  LocalUserRepository(): 
    _localDb = LocalDb.getInstance;

  void saveUser(LocalUser user) async {
    int res = await _localDb.save('users', user.toMap());

    if (res == 0) {
      _logger.severe('Local user $user has not been saved');
    }
  }

  void updateUser(LocalUser user) {
    if (user.id == null) {
      _logger.info('Local user $user would not be saved');
    }

    _localDb.update('users', user.toMap(), whereClause: 'id = ?', whereArgs: [user.id]);
  }

  void doesUserExist() async {
    List<Map<String, Object?>> rs = await _localDb.rawQuery(
      'select count(1) as cnt from users'
    );

    var result = rs.first['cnt'];
    _logger.info("found $result results ");
  }
}