import 'dart:async';

import 'package:flutter/services.dart';

import 'alarm_meta.dart';
import 'android.dart';
import 'ios.dart';

/// Player for system sounds like ringtone, alarm and notification.
/// * On Android it uses system default sounds for each ringtone type.
/// * On iOS it uses some hardcoded values for each type.
class RingtonePlayer {
  static const MethodChannel _channel = MethodChannel('ringtone_player');

  /// Using this method which allows you to specify individual sound as
  /// you wish to play for each platform.
  ///
  /// * [alarm] is an Android only flag that plays the given sound as an alarm,
  /// that is, phone will make sound even if it is in silent or vibration mode.
  /// If sound is played as alarm the plugin will run the service in foreground.
  /// * Therefore you also have to set [alarmMeta].
  /// * [loop] is to enable looping on system sounds like ringtone, alarm and
  /// notification. Only in Android.
  ///
  /// See also:
  ///  * [AndroidSound]
  ///  * [IosSound]
  static Future<void> play({
    required AndroidSound android,
    required IosSound ios,
    double? volume = 1.0,
    bool? loop,
    bool? alarm,
    AlarmMeta? alarmMeta,
  }) async {
    try {
      var args = <String, dynamic>{
        'android': android.value,
        'ios': ios.value,
      };
      if (loop != null) args['loop'] = loop;
      if (volume != null) args['volume'] = volume;
      if (alarm != null) args['alarm'] = alarm;
      if (alarmMeta != null) args['alarmMeta'] = alarmMeta.toMap();

      _channel.invokeMethod('play', args);
    } on PlatformException {}
  }

  /// Play default alarm sound
  static Future<void> alarm({
    double? volume,
    bool loop = true,
    bool alarm = true,
    AlarmMeta? alarmMeta,
  }) async =>
      play(
        android: Android.alarm,
        ios: Ios.alarm,
        volume: volume,
        loop: loop,
        alarm: alarm,
        alarmMeta: alarmMeta,
      );

  /// Play default notification sound
  static Future<void> notification({
    double? volume,
    bool loop = false,
    bool alarm = false,
    AlarmMeta? alarmMeta,
  }) async =>
      play(
        android: Android.notification,
        ios: Ios.triTone,
        volume: volume,
        loop: loop,
        alarm: alarm,
        alarmMeta: alarmMeta,
      );

  /// Play default system ringtone
  static Future<void> ringtone(
          {double? volume,
          bool loop = true,
          bool alarm = false,
          AlarmMeta? alarmMeta}) async =>
      play(
        android: Android.ringtone,
        ios: Ios.electronic,
        volume: volume,
        loop: loop,
        alarm: alarm,
        alarmMeta: alarmMeta,
      );

  /// Stop sounds like alarm & ringtone on Android.
  /// This is no-op on iOS.
  static Future<void> stop() async {
    try {
      _channel.invokeMethod('stop');
    } on PlatformException {}
  }
}
