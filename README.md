# ringtone_player

A player for system default ringtone, alarm and notification.

[![Pub Version](https://img.shields.io/pub/v/ringtone_player)](https://pub.dartlang.org/packages/ringtone_player)

## Installation

Add following dependencies to your pubspec.yaml:

```yaml
dependencies:
  ringtone_player: ^0.0.3 #latest version
```

## Usage

Add following import to your code:

```dart
import 'package:ringtone_player/ringtone_player.dart';
```

Then simply call this to play system default notification sound:

```dart
RingtonePlayer.ringtone();
```

There's also this generic method allowing you to specify in detail what kind of ringtone should be played:

```dart
RingtonePlayer.play(
  alarmMeta: AlarmMeta(
    'dev.cruv.ringtone_player_example.MainActivity',
    'ic_alarm_notification',
    contentTitle: 'Alarm',
    contentText: 'Alarm is active',
    subText: 'Subtext',
  ),
  android: Android.ringtone,
  ios: Ios.electronic,
  loop: true, // Android only - API >= 28
  volume: 1.0, // Android only - API >= 28
  alarm: true, // Android only - all APIs
);

```

### .play() optional attributes

| Attribute       |  Description                                                                 |
| --------------  | ---------------------------------------------------------------------------- |
| `bool` loop     | Enables loop of ringtone. If Requires                                        |
| `double` volume | Sets ringtone volume in range 0 to 1.0.                                      |
| `bool` alarm    | Allows to ignore device's silent/vibration mode and play given sound anyway. |


To stop looped ringtone please use:

```dart
RingtonePlayer.stop();
```

Above works only on Android, and please note that by default Alarm & Ringtone sounds are looped.

## Default sounds

| Method           | Android | iOS |
| ---------------- | ------- | --- |
| alarm            | [System#DEFAULT_ALARM_ALERT_URI](https://developer.android.com/reference/android/provider/Settings.System.html#DEFAULT_ALARM_ALERT_URI) | Ios.alarm |
| notification     | [System#DEFAULT_NOTIFICATION_URI](https://developer.android.com/reference/android/provider/Settings.System.html#DEFAULT_NOTIFICATION_URI) | Ios.triTone |
| ringtone         | [System#DEFAULT_RINGTONE_URI](https://developer.android.com/reference/android/provider/Settings.System.html#DEFAULT_RINGTONE_URI) | Ios.electronic |

## Note for AndroidManifest.xml

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="dev.cruv.ringtone_player_example">

    <!-- Must add this line -->
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <!-- -->

    <application
        android:icon="@mipmap/ic_launcher"
        android:label="Ringtone_player_example"
        tools:ignore="AllowBackup">

        <!-- Must add this line -->
        <service android:name="dev.cruv.ringtone_player.PlayerService" />
        <!-- -->

        <activity>...</activity>
        ...
    </application>
</manifest>
```

## Note on iOS sound

If you want to use any other sound on iOS you can always specify a valid Sound ID and manually construct [IosSound]:

```dart
RingtonePlayer.play(
  android: Android.notification,
  ios: const IosSound(1023),
  looping: true,
  volume: 0.1,
);
```

##License

[MIT License](https://github.com/cruvdev/ringtone_player/blob/master/LICENSE)
