import 'package:bg_app_ui/widgets/commons/buttons/bg_app_home_button.dart';
import 'package:flutter/material.dart';

class RegTeseraUserPage extends StatefulWidget {
  const RegTeseraUserPage({super.key});

  @override
  State<StatefulWidget> createState() => _RegTeseraUserPageState();
}

class _RegTeseraUserPageState extends State<RegTeseraUserPage> {

  final TextEditingController _teseraUsenameController = TextEditingController();

  late FocusNode _teseraUsernameFocusNode;
  late FocusAttachment _teseraUsernameFocusAttachment;

  void _handleTeseraUsernameInput() {

  }

  void _handleTeseraUsernameFocusChanged() {

  }

  @override
  void initState() {
    _teseraUsernameFocusNode = FocusNode(debugLabel: "_teseraUsernameFocusNode");
    _teseraUsenameController.addListener(_handleTeseraUsernameInput);

    _teseraUsernameFocusNode.addListener(_handleTeseraUsernameFocusChanged);
    _teseraUsernameFocusAttachment = _teseraUsernameFocusNode.attach(context);

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: BackButton(),
      ),
      body: Center(
        child: Column(
          children: [
            Spacer(),
            Padding(
              padding: EdgeInsetsGeometry.symmetric(horizontal: 40.0),
              child: TextField(
              controller: _teseraUsenameController,
              focusNode: _teseraUsernameFocusNode,
              decoration: InputDecoration(
                hint: Text("Введите имя пользователя Tesera"),
                hintStyle: TextStyle(
                  color: Colors.grey

                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(20.0)
                )
              ),
            ),
            )
            ,
            Spacer()
          ],
        ),
      ),
      floatingActionButton: BgAppHomeButton(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }

  @override
  void dispose() {
    _teseraUsenameController.removeListener(_handleTeseraUsernameInput);
    _teseraUsernameFocusAttachment.detach();
    
    _teseraUsenameController.dispose();
    _teseraUsernameFocusNode.dispose();
    super.dispose();
  }
}