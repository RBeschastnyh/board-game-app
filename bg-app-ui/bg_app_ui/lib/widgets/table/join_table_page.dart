import 'package:bg_app_ui/widgets/commons/buttons/bg_app_home_button.dart';
import 'package:flutter/material.dart';

class JoinTablePage extends StatefulWidget {
  const JoinTablePage({super.key});

  @override
  State<StatefulWidget> createState() => _JoinTablePageState();
}

class _JoinTablePageState extends State<JoinTablePage> {
  final TextEditingController _tableCodeController = TextEditingController();

  late FocusNode _tableCodeFocus;
  late FocusAttachment _tableCodeFocusAttachment;

  void _handleTableFocusChanged() {

  }

  void _handleTableCodeTextChanged() {
    String currentText = _tableCodeController.value.text;

    print(currentText);
    
    _tableCodeController.value = _tableCodeController.value.copyWith(
      text: currentText.length > 5 ? currentText.toUpperCase().substring(0, 5) : currentText.toUpperCase(),
    );
  }

  @override
  void initState() {
    _tableCodeFocus = FocusNode(debugLabel: "_tableCodeFocus");
    _tableCodeFocus.addListener(_handleTableFocusChanged);

    _tableCodeController.addListener(_handleTableCodeTextChanged);

    _tableCodeFocusAttachment = _tableCodeFocus.attach(context);

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    _tableCodeFocusAttachment.reparent();

    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          onPressed: () => Navigator.pop(context),
          icon: Icon(Icons.arrow_back, color: Colors.black),
        ),
      ),
      body: Center(
        child: Column(
          children: [
            Spacer(),
            Padding(
              padding: EdgeInsetsGeometry.symmetric(horizontal: 50.0),
              child: TextField(
                controller: _tableCodeController,
                focusNode: _tableCodeFocus,
                decoration: InputDecoration(
                  hint: Text("Введите код стола"),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(20.0)),
                    // borderSide: BorderSide(
                    //   style: BorderStyle.solid,
                    //   color: Colors.amberAccent,
                    //   width: 10.0
                    // )
                  )
                ),
              ),
            ),
            Spacer(),
            TextButton(
              onPressed: () {
                print("присоединяюсь к игре");
              }, 
              style: TextButton.styleFrom(
                backgroundColor: Colors.amberAccent,
                foregroundColor: Colors.black,
              ),
              child: Text("Присоедититься")
            ),
            Spacer(),
          ],
        ),
      ),
      floatingActionButton: BgAppHomeButton(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }

  @override
  void dispose() {
    _tableCodeController.removeListener(_handleTableCodeTextChanged);
    _tableCodeFocus.removeListener(_handleTableFocusChanged);

    _tableCodeFocus.dispose();
    _tableCodeController.dispose();

    super.dispose();
  }
}
