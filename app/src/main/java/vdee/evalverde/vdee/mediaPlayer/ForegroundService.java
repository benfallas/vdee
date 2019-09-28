package vdee.evalverde.vdee.mediaPlayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.mainScreen.MainActivity;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "vdeeForegroundChannel";
    public final static String START_STOP_KEY = "startStopKey";
    public final static String START_SERVICE_FLAG = "startService";
    public final static String STOP_SERVICE_FLAG = "stopService";

    public SimplePlayer simplePlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        simplePlayer = SimplePlayer.getSimplePlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();

        if (action == null) {
            action = "";
        }

        if (action.equals(Constants.ACTION.START_SERVICE)) {

            createNotificationChannel();
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);

            Intent playIntent = new Intent(this, ForegroundService.class);
            playIntent.setAction(Constants.ACTION.ACTION_PLAY);
            PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent stopIntent = new Intent(this, ForegroundService.class);
            stopIntent.setAction(Constants.ACTION.ACTION_STOP);
            PendingIntent pendingStopIntent = PendingIntent.getService(this, 0,
                    stopIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Estas Escuchando")
                    .setContentText("Voz del Evangelio Eterno")
                    .setSmallIcon(R.drawable.vdee_logo)
                    .addAction(R.drawable.play_button, "Play", pendingPlayIntent)
                    .addAction(R.drawable.stop_button, "Stop", pendingStopIntent)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .build();

            startForeground(1, notification);
        } else if (action.equals(Constants.ACTION.STOP_SERVICE)){
            stopForeground(true);
            stopSelf();
        } else if (action.equals(Constants.ACTION.ACTION_PLAY)) {
            if (!simplePlayer.isInitialized()) {
                simplePlayer.initPlayer();
            }
        } else if (action.equals(Constants.ACTION.ACTION_STOP)) {
            if (simplePlayer.isInitialized()) {
                simplePlayer.releasePlayer();
            }

        } else {
            Log.d("ForegroundSErvice", "empty");
        }


        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
