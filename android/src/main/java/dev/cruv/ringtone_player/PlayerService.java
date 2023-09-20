package dev.cruv.ringtone_player;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class PlayerService extends Service {
    public static final String RINGTONE_META_INTENT_EXTRA_KEY = "ringtone-meta";
    private static final String CHANNEL_ID = "player-service-channel";

    private Ringtone ringtone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final Bundle extras = intent.getExtras();
            if (extras == null) {
                throwInvalidArgumentsException();
            }

            final PlayerMeta playerMeta = (PlayerMeta) extras.getSerializable(RINGTONE_META_INTENT_EXTRA_KEY);
            if (playerMeta == null) {
                throwInvalidArgumentsException();
            }

            if (playerMeta.getAlarm()) {
                startForeground(playerMeta);
            } else {
                stopForeground(true);
            }

            stopRingtone();
            startRingtone(playerMeta);
        } else {
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopRingtone();
        super.onDestroy();
    }

    private void throwInvalidArgumentsException() {
        throw new IllegalArgumentException("Invalid arguments given");
    }

    private void startForeground(PlayerMeta playerMeta) {
        createNotificationChannel();

        final AlarmMeta alarmMeta = playerMeta.getAlarmMeta();
        validate(alarmMeta);

        final Class<?> activityClass = getActivityClassLaunchedByNotificationIntent(playerMeta);
        final Intent notificationIntent = new Intent(this, activityClass);
        final int iconDrawableResourceId = getResources().getIdentifier(alarmMeta.getDrawableResourceIcon(), "drawable", getPackageName());
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        final Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(iconDrawableResourceId)
                .setContentTitle(alarmMeta.getContentTitle())
                .setContentText(alarmMeta.getContentText())
                .setSubText(alarmMeta.getSubText())
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    private void stopRingtone() {
        if (ringtone != null) {
            ringtone.stop();
        }
        ringtone = null;
    }

    private void startRingtone(PlayerMeta playerMeta) {
        ringtone = getConfiguredRingtone(playerMeta);
        if(ringtone != null) {
            ringtone.play();
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Foreground service channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            serviceChannel.setLightColor(Color.RED);
            serviceChannel.enableLights(true);
            final NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void validate(AlarmMeta alarmMeta) {
        if (alarmMeta.getLaunchedByIntent() == null || alarmMeta.getDrawableResourceIcon() == null) {
            throwInvalidArgumentsException();
        }
    }

    private Class<?> getActivityClassLaunchedByNotificationIntent(PlayerMeta playerMeta) {
        final String className = playerMeta.getAlarmMeta().getLaunchedByIntent();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class '" + className + "' not found");
        }
    }

    private Ringtone getConfiguredRingtone(PlayerMeta meta) {
        final Uri uri = getRingtoneUri(meta.getKind());
        final Ringtone ringtone = RingtoneManager.getRingtone(this, uri);

        if(ringtone != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ringtone.setLooping(meta.getLoop());
                if (meta.getVolume() != null) {
                    ringtone.setVolume(meta.getVolume());
                }
            }
            if (meta.getAlarm()) {
                ringtone.setStreamType(AudioManager.STREAM_ALARM);
            }
        }

        return ringtone;
    }

    private Uri getRingtoneUri(int kind) {
        int ringtoneType = -1;

        switch (kind) {
            case 1:
                ringtoneType = RingtoneManager.TYPE_ALARM;
                break;

            case 2:
                ringtoneType = RingtoneManager.TYPE_NOTIFICATION;
                break;

            case 3:
                ringtoneType = RingtoneManager.TYPE_RINGTONE;
                break;

            default:
                throwInvalidArgumentsException();
        }
        return RingtoneManager.getDefaultUri(ringtoneType);
    }
}
