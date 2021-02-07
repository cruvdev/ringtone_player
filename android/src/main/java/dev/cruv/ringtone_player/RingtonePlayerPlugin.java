package dev.cruv.ringtone_player;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * RingtonePlayerPlugin
 */
public class RingtonePlayerPlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;
    private Context context;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        this.context = binding.getApplicationContext();
        channel = new MethodChannel(binding.getBinaryMessenger(), "ringtone_player");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        try {
            if (call.method.equals("play")) {
                final PlayerMeta meta = createPlayerMeta(call);
                startPlayer(meta);
                result.success(null);
            } else if (call.method.equals("stop")) {
                stopPlayer();
                result.success(null);
            }
        } catch (Exception e) {
            result.error("Exception", e.getMessage(), null);
        }
    }

    private PlayerMeta createPlayerMeta(MethodCall call) {
        if (!call.hasArgument("android")) {
            throw new IllegalArgumentException("android argument is missing");
        }

        final PlayerMeta meta = new PlayerMeta();
        final int kind = getMethodCallArgument(call, "android");
        meta.setKind(kind);
        final Boolean loop = getMethodCallArgument(call, "loop");
        meta.setLoop(loop);
        final Boolean alarm = getMethodCallArgument(call, "alarm");
        meta.setAlarm(alarm);
        final Double volume = getMethodCallArgument(call, "volume");
        if (volume != null) {
            meta.setVolume(volume.floatValue());
        }

        if (meta.getAlarm()) {
            final String alarmMetaKey = "alarmMeta";

            if (call.hasArgument(alarmMetaKey)) {
                final Map<String, Object> notificationMetaValues = getMethodCallArgument(call, alarmMetaKey);
                final AlarmMeta notificationMeta = new AlarmMeta(notificationMetaValues);
                meta.setAlarmMeta(notificationMeta);
            } else {
                throw new IllegalArgumentException("if asAlarm=true you have to deliver '" + alarmMetaKey + "'");
            }
        }

        return meta;
    }

    private void startPlayer(PlayerMeta meta) {
        final Intent intent = createServiceIntent();
        intent.putExtra(PlayerService.RINGTONE_META_INTENT_EXTRA_KEY, meta);

        if (meta.getAlarm()) {
            ContextCompat.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    private void stopPlayer() {
        final Intent intent = createServiceIntent();
        context.stopService(intent);
    }

    private <ArgumentType> ArgumentType getMethodCallArgument(MethodCall call, String key) {
        return call.argument(key);
    }

    private Intent createServiceIntent() {
        return new Intent(context, PlayerService.class);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
