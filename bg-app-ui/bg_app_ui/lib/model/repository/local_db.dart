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
          db
            .execute(
              '''create table if not exists users (
                id integer primary key AUTOINCREMENT not null, 
                username text not null, 
                tesera_username text, 
                last_visited text
                )'''
            );
          db.execute('''
            create table if not exists games (
              id integer not null primary key autoincrement,
              tesera_game_id integer,
              game_title text not null,
              eng_game_title text,
              user_rating numeric,
              is_addition boolean not null,
              production_year integer,
              players_min int,
              players_max int,
              recommended_players_min int,
              recommended_players_max int
            )
          ''');
          db.execute('''
            create table if not exists user_game_map (
              id integer primary key autoincrement not null,
              user_id integer not null,
              game_id integer not null,
              foreign key (user_id) references users(id)
              foreign key (game_id) references games(id)
            )
          ''');
          return db.execute('''create table if not exists sessions (
            id integer primary key AUTOINCREMENT not null,
            session_id text not null,
            username text not null,
            valid_from text not null,
            valid_until text not null,
            last_login text not null
          )''');
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
