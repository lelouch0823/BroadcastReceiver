package com.bjw.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class Myservice extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload () {
            Logger.d("startDownload");
        }
        public int getProgress () {
            Logger.d("getProgress");
            return 0;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("Service stop");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("Service StartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(Myservice.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this)
                //设置图标
                .setSmallIcon(R.drawable.ic_accessible_red_500_18dp)
                //设置下拉后显示的通知内容
                .setContentText("This is content")
                //设置下拉后显示的通知标题
                .setContentTitle("This is title")
                //设置点击通知后的动作
                .setContentIntent(pendingIntent)
                .setTicker("TickerText:" + "kdada")
                .build();
        startForeground(1,notification);

        Logger.d("Service Start");
    }
}
