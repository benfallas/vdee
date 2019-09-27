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

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.mainScreen.MainActivity;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "vdeeForegroundChannel";
    public final static String START_STOP_KEY = "startStopKey";
    public final static String START_SERVICE_FLAG = "startService";
    public final static String STOP_SERVICE_FLAG = "stopService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String startStopValue = intent.getStringExtra(START_STOP_KEY);

        if (startStopValue.equals(START_SERVICE_FLAG)) {

            createNotificationChannel();
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Foreground Service")
                    .setSmallIcon(R.drawable.vdee_logo)
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(1, notification);
        } else {
            stopForeground(true);
            stopSelf();
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
