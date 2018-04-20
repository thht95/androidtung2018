package fithou.tam.smartnote.System;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import fithou.tam.smartnote.CustomizeNote.ResultActivity;
import fithou.tam.smartnote.Main.MainActivity;
import fithou.tam.smartnote.R;

/**
 * Created by BapNo on 10/23/2016.
 */

public class NotifyService extends Service {
    private Handler hd;
    private int count = 10;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        hd = new Handler();
    }
    private void showNotification() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Khởi tạo layout notification
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                        .setContentTitle("Smart Note")
                        .setContentText("Nhắc nhở công việc!").setSound(alarmSound);


        //========================
        //Nếu như muốn start lên activity thì viết thêm đoạn code này
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        //=================
        //Show lên notification
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Đây chính là nói xử lý công việc liên quan đến started service
        //Nội dung service thì nên bỏ vào một thread vì service chạy ở Main UI Thread
        countTime();
        return super.onStartCommand(intent, flags, startId);
    }

    private void countTime() {
        hd.postDelayed(logRa, 1000);
    }

    private Runnable logRa = new Runnable() {
        @Override
        public void run() {

            if (count >= 0) {
                count--;
                Log.w("Count", count + "");
                countTime();
            }
            if (count==0){
                showNotification();
            }

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        hd.removeCallbacks(logRa);

        Log.w("Ket Thuc", "Service tu ket thuc");
    }
}
