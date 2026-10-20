import 'package:flutter/material.dart';

class DefaultCancelTextButton extends StatelessWidget {
  final String text;
  final VoidCallback? callback;

  const DefaultCancelTextButton({super.key, required this.text, this.callback});

  @override
  Widget build(BuildContext context) {
    return TextButton(
      onPressed: () => callback ?? Navigator.pop(context),
      style: TextButton.styleFrom(
        backgroundColor: Colors.red,
        foregroundColor: Colors.black,
      ),
      child: Text(text),
    );
  }
}
