import 'package:bg_app_ui/model/repository/local_db.dart';
import 'package:bg_app_ui/model/repository/session/local_session.dart';
import 'package:bg_app_ui/utils/logger.dart';
import 'package:logging/logging.dart';

class SessionRepository {
  static final Logger _logger = initLogger('SessionRepository');

  final LocalDb _localDb;

  SessionRepository() : _localDb = LocalDb.getInstance;

  Future<String?> checkIfUserHasBeenAuthorized() async {
    var res = await _localDb.rawQuery('''
        select username 
        from sessions 
        where date(valid_until) <= date('now')
        order by date(valid_from) desc 
        limit 1''');

    if (res.isEmpty) {
      _logger.info('No user has been authorized');
      return null;
    }

    _logger.finer('User $res has been authenticated last');

    return res.first['username'] as String?;
  }

  Future<bool> checkCurrentLoginEverAuthenticated(String login) async {
    var res = await _localDb.rawQuery(
      '''
        select count(1) as cnt
        from sessions
        where username=?
      ''',
      args: [login],
    );

    return res.first['cnt'] as int > 0;
  }

  Future<bool> updateSessionFor(String login) async {
    var now = DateTime.now();

    var res = await _localDb.rawQuery('''
      update session set valid_from = ?, valid_util = ? where username = ?
    ''', args: [now, now.add(Duration(days: 30)), login]);

    _logger.info('$res');

    return true;
  }

  Future<String?> getLastSessionIdForUser(String login) async {
    var res = await _localDb.rawQuery(
      '''
        select session_id from sessions where username = ? and valid_until <= ?
      ''', args: [login, DateTime.now()]
    );

    if (res.isEmpty) {
      return null;
    }

    return res.first['session_id'] as String?;
  }

  void save(LocalSession? session) async {
    await _localDb.save("sessions", session!.toMap());
  }
}
