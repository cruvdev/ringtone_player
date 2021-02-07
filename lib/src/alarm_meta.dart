class AlarmMeta {
  final String launchedByIntent;
  final String drawableResourceIcon;
  final String contentTitle;
  final String contentText;
  final String subText;

  AlarmMeta(
    this.launchedByIntent,
    this.drawableResourceIcon, {
    this.contentTitle,
    this.contentText,
    this.subText,
  });

  Map<String, dynamic> toMap() => {
        'launchedByIntent': launchedByIntent,
        'drawableResourceIcon': drawableResourceIcon,
        'contentTitle': contentTitle,
        'contentText': contentText,
        'subText': subText,
      };
}
