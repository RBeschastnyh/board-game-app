// ignore: depend_on_referenced_packages
import 'package:path/path.dart';
// ignore: depend_on_referenced_packages
import 'package:sqflite/sqflite.dart';

class LocalDb {
  static LocalDb? _instance;

  final Future<Database> _db;

  const LocalDb._({
    required Future<Database> newDb,
  }) : _db = newDb;

  static LocalDb get getInstance => _instance!;

  static Future<LocalDb> initialize() async {
    if (_instance == null) {
      deleteDatabase(join(await getDatabasesPath(), 'party.db'));
      Future<Database> newDb = openDatabase(
        join(await getDatabasesPath(), 'party.db'),
        onOpen: (db) => {
          print('OPENED')
        },
        onCreate: (db, version) {
          print('CREATED');
          db.execute('''create table if not exists sessions (
            id integer primary key AUTOINCREMENT not null,
            session_id text not null,
            username text not null,
            valid_from text not null,
            valid_until text not null 
          )''');
          return db
            .execute(
              '''create table if not exists users (
                id integer primary key AUTOINCREMENT not null, 
                username text not null, 
                tesera_username text, 
                last_visited text
                )'''
            );
        },
        version: 1
      );

      _instance = LocalDb._(newDb: newDb);
    }

    return Future.syncValue(_instance!);
  }

  Future<List<Map<String, Object?>>> rawQuery(String sql, {List<Object?>? args}) async {
    if (args != null) {
      return (await _db).rawQuery(sql, args);
    }

    return (await _db).rawQuery(sql);
  }

  // Future<List<Map<String, Object?>>> query() {
  //   (await _db).query(table)
  // }

  Future<int> save(String tableName, Map<String, Object?> rawObject) async {
    return (await _db).insert(tableName, rawObject);
  }

  Future<int> update(String tableName, Map<String, Object?> rawObject, {String? whereClause, List<Object?>? whereArgs}) async {
    if (whereClause == null) {
      return await (await _db).update(tableName, rawObject); 
    }

    if (whereArgs == null) {
      return await (await _db).update(tableName, rawObject, where: whereClause);
    }

    return await (await _db).update(tableName, rawObject, where: whereClause, whereArgs: whereArgs);
  }
}
