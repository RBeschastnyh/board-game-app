import 'package:bg_app_ui/models/games.dart';
import 'package:bg_app_ui/widgets/commons/buttons/bg_app_home_button.dart';
import 'package:flutter/material.dart';

class GamesListPage extends StatefulWidget {
  const GamesListPage({super.key});

  @override
  State<StatefulWidget> createState() => _GamesListPageState();
}

class _GamesListPageState extends State<GamesListPage> {
  // List<Games> _games = [];

  List<Games> _games = [
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4),
    Games.withoutImage("Саграда", "Описание Саграды", 2, 4, 4)
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(leading: BackButton()),
      body: Center(
        child: _games.isEmpty ? _getEmptyListBody() : _createGamesListView(),
      ),
      floatingActionButton: BgAppHomeButton(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }

  Widget _createGamesListView() {
    return ListView.separated(
      separatorBuilder: (context, index) => SizedBox(height: 15.0),
      itemCount: _games.length,
      itemBuilder: (context, index) {
        return ListTile(
          shape: BoxBorder.fromLTRB(top: BorderSide(color: Colors.black, style: BorderStyle.solid)),
          onLongPress: () => print("Меня долго зажали"),
          leading: Image.asset(
            "assets/images/noun_Meeple_128.png",
            width: 128.0,
            height: 128.0,
          ),
          title: Container(
            child: Row(
              children: [
                Column(
                  children: [
                    Text(_games[index].getDescription ?? ""),
                    Row(
                      children: [
                        Column(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text("Сыграно ${_games[index].getGamesPlayed}"),
                            Text("Побед ${_games[index].getGamesWon}"),
                            Text("Win-rate ${_games[index].winRate}"),
                          ],
                        ),
                        TextButton(
                          onPressed: () => print("Жмяк"),
                          child: Text("Придумать"),
                        ),
                      ],
                    ),
                  ],
                ),
              ],
            ),
          ),
        );
      },
    );
  }

  Widget _getEmptyListBody() {
    return Column(
      children: [Spacer(), Text("В списке игр пока пусто :("), Spacer()],
    );
  }
}
