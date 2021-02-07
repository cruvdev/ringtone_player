package dev.cruv.ringtone_player;

import java.io.Serializable;

public class PlayerMeta implements Serializable {
    private int kind;
    private Float volume;
    private Boolean loop;
    private Boolean alarm;
    private AlarmMeta alarmMeta;

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getVolume() {
        return volume;
    }

    public void setLoop(Boolean loop) {
        this.loop = loop;
    }

    public boolean getLoop() {
        return Boolean.TRUE.equals(loop);
    }

    public void setAlarm(Boolean alarm) {
        this.alarm = alarm;
    }

    public boolean getAlarm() {
        return Boolean.TRUE.equals(alarm);
    }

    public AlarmMeta getAlarmMeta() {
        return alarmMeta;
    }

    public void setAlarmMeta(AlarmMeta alarmMeta) {
        this.alarmMeta = alarmMeta;
    }
}
