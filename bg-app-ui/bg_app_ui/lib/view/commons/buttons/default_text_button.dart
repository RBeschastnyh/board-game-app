import 'package:flutter/material.dart';

class DefaultTextButton extends StatelessWidget {
  final String text;
  final VoidCallback? callback;

  const DefaultTextButton({super.key, required this.text, this.callback});

  @override
  Widget build(BuildContext context) {
    return TextButton(
      onPressed: callback,
      style: TextButton.styleFrom(
        backgroundColor: Colors.cyan,
        foregroundColor: Colors.black,
      ),
      child: Text(text),
    );
  }
}
