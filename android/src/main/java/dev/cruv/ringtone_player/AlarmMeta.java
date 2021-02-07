package dev.cruv.ringtone_player;

import java.io.Serializable;
import java.util.Map;

public class AlarmMeta implements Serializable {
    private final Map<String, Object> notificationMetaValues;

    public AlarmMeta(Map<String, Object> notificationMetaValues) {
        this.notificationMetaValues = notificationMetaValues;
    }

    public CharSequence getContentTitle() {
        return (CharSequence) notificationMetaValues.get("contentTitle");
    }

    public CharSequence getContentText() {
        return (CharSequence) notificationMetaValues.get("contentText");
    }

    public CharSequence getSubText() {
        return (CharSequence) notificationMetaValues.get("subText");
    }

    public String getDrawableResourceIcon() {
        return (String) notificationMetaValues.get("drawableResourceIcon");
    }

    public String getLaunchedByIntent() {
        return (String) notificationMetaValues.get("launchedByIntent");
    }
}
