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

  bool _validTableCode = false;
  late String _currentText;

  void _handleTableFocusChanged() {
    _validTableCode = RegExp(r'[0-9A-Z]{5}').hasMatch(_tableCodeController.text);
  }

  void _handleTableCodeTextChanged() {
    _currentText = _tableCodeController.value.text;

    print(_currentText);

    _tableCodeController.value = _tableCodeController.value.copyWith(
      text: _formatTableCode(_currentText),
      selection: TextSelection(
        baseOffset: _tableCodeController.value.text.length,
        extentOffset: _tableCodeController.value.text.length % 6,
      ),
    );
  }

  String _formatTableCode(String currentText) {
    if (currentText.isEmpty) {
      return currentText;
    }

    if (RegExp(r'[a-zA-Z0-9]').hasMatch(currentText.characters.last)) {
      return currentText.length > 5
          ? currentText.toUpperCase().substring(0, 5)
          : currentText.toUpperCase();
    }

    return currentText.characters.skipLast(1).toString();
  }

  @override
  void initState() {
    _currentText = "";
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
        leading: BackButton(),
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
                  errorText: _currentText.isEmpty || _currentText.isNotEmpty && _validTableCode ? null : "Некорректный код",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(20.0)),
                    // borderSide: BorderSide(
                    //   style: BorderStyle.solid,
                    //   color: Colors.amberAccent,
                    //   width: 10.0
                    // )
                  ),
                ),
              ),
            ),
            Spacer(),
            TextButton(
              onPressed: _validTableCode ? () {
                print("присоединяюсь к игре");
              } : null,
              style: TextButton.styleFrom(
                backgroundColor: Colors.amberAccent,
                foregroundColor: Colors.black
              ),
              child: Text("Присоедититься"),
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
