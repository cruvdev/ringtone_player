import 'package:flutter/material.dart';
import 'package:ringtone_player/ringtone_player.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.black,
          title: const Text('Ringtone player'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              Button(
                child: const Text('playAlarm'),
                onPressed: () {
                  RingtonePlayer.alarm(
                    alarmMeta: AlarmMeta(
                      'dev.cruv.ringtone_player_example.MainActivity',
                      'ic_alarm_notification',
                      contentTitle: 'Alarm',
                      contentText: 'Alarm is active',
                      subText: 'Subtext',
                    ),
                  );
                },
              ),
              Button(
                child: const Text('playAlarm asAlarm: false'),
                onPressed: () {
                  RingtonePlayer.alarm(alarm: false);
                },
              ),
              Button(
                child: const Text('playNotification'),
                onPressed: () {
                  RingtonePlayer.notification();
                },
              ),
              Button(
                child: const Text('playRingtone'),
                onPressed: () {
                  RingtonePlayer.ringtone();
                },
              ),
              Button(
                child: const Text('playRingtone as alarm'),
                onPressed: () {
                  RingtonePlayer.ringtone(
                    alarm: true,
                    alarmMeta: AlarmMeta(
                      'dev.cruv.ringtone_player_example.MainActivity',
                      'ic_alarm_notification',
                      contentTitle: 'Alarm',
                      contentText: 'Alarm is active',
                      subText: 'Subtext',
                    ),
                  );
                },
              ),
              Button(
                child: const Text('play'),
                onPressed: () {
                  RingtonePlayer.play(
                    android: Android.ringtone,
                    ios: Ios.electronic,
                    loop: true,
                    volume: 1.0,
                  );
                },
              ),
              Button(
                child: const Text('stop'),
                onPressed: () {
                  RingtonePlayer.stop();
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class Button extends StatefulWidget {
  Button({Key? key, this.onPressed, this.child}) : super(key: key);

  final VoidCallback? onPressed;
  final Widget? child;

  @override
  _ButtonState createState() => _ButtonState();
}

class _ButtonState extends State<Button> {
  @override
  Widget build(BuildContext context) {
    return RaisedButton(
      color: Colors.white,
      shape: RoundedRectangleBorder(
        side: BorderSide(),
        borderRadius: BorderRadius.circular(20),
      ),
      onPressed: widget.onPressed,
      child: widget.child,
    );
  }
}
