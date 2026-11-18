import 'dart:convert';

import 'package:bg_app_ui/exceptions/user_not_found_exception.dart';
import 'package:bg_app_ui/model/repository/local_db.dart';
import 'package:bg_app_ui/model/repository/user/local_user.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:logging/logging.dart';

import '../../../exceptions/severe_error_exception.dart';

class LocalUserRepository {
  static final Logger _logger = initLogger('LocalUserRepository');

  final LocalDb _localDb;

  LocalUserRepository(): 
    _localDb = LocalDb.getInstance;

  Future<int> saveUser(LocalUser user) async {
    int res = await _localDb.save('users', user.toMap());

    if (res == 0) {
      _logger.severe('Local user $user has not been saved');
      return -1;
    }

    List<Map<String, Object?>> savedUser = await _localDb.query(
        'users',
        where: 'username = ?', whereArgs: [user.username.toLowerCase()]
    );

    return savedUser.first['id'] as int;
  }

  void updateUser(LocalUser user) {
    if (user.id == null) {
      _logger.info('Local user $user would not be saved');
    }

    _localDb.update('users', user.toMap(), whereClause: 'id = ?', whereArgs: [user.id]);
  }

  Future<bool> doesUserExist() async {
    List<Map<String, Object?>> rs = await _localDb.rawQuery(
      'select count(1) as cnt from users'
    );

    var result = rs.first['cnt'];
    _logger.info("found $result results ");
    
    return result as int > 0;
  }

  Future<LocalUser> findByLogin(String login) async {
    List<Map<String, Object?>> res = await _localDb.query('users', where: 'username = ?', whereArgs: [login.toLowerCase()]);
    
    if (res.isEmpty) {
      _logger.severe("Not found user for login $login");
      throw UserNotFoundException("Пользователь не найден!");
    }
    
    if (res.length > 1) {
      _logger.shout("Duplicated user login $login!!! Intervene ASAP");
      throw SevereErrorException('Возникла ошибка. Сообщите о ней');
    }
    
    return LocalUser.fromMap(res.first);
  }
}