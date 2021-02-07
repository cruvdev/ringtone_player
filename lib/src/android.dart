/// Android sound kind.
///
/// Valid values are 1-3, as specified in [AndroidSounds].
class AndroidSound {
  final int value;

  const AndroidSound(int value)
      : assert(value >= 1),
        assert(value <= 3),
        value = value;
}

/// Default [AndroidSound] values.
class Android {
  Android._();

  /// System default alarm sound
  static const AndroidSound alarm = AndroidSound(1);

  /// System default notification sound
  static const AndroidSound notification = AndroidSound(2);

  /// System default ringtone sound
  static const AndroidSound ringtone = AndroidSound(3);
}
