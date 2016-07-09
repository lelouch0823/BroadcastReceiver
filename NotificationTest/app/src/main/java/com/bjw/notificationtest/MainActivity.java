package com.bjw.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private Bundle mButton16;
    private Button notdfiButton;
    private Button n16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.n16 = (Button) findViewById(R.id.n16);
        this.notdfiButton = (Button) findViewById(R.id.notdfiButton);
        mButton = (Button) findViewById(R.id.notdfiButton);
        n16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //高于API Level 16 (Android 4.1.2)的创建通知方法
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager manager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                //音频文件的Uri地址
                Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));
                //震动间隔
                long[] vibrates = {0,1000,1000,1000};
               Notification notification = new Notification.Builder(MainActivity.this)
                       //设置图标
                        .setSmallIcon(R.drawable.p1)
                       //设置下拉后显示的通知内容
                        .setContentText("This is content")
                       //设置下拉后显示的通知标题
                        .setContentTitle("This is title")
                       //设置点击通知后的动作
                        .setContentIntent(pendingIntent)
                       .setTicker("TickerText:" + "kdada")
                        .build();
                //设置通知声音
                notification.sound = soundUri;
                //设置震动
                notification.vibrate = vibrates;
                //自定义设置LED
                notification.ledARGB = Color.GREEN;
                //亮灯时长
                notification.ledOnMS = 1000;
                //熄灯时长
                notification.ledOffMS = 1000;
                //指定行为
                notification.flags = Notification.FLAG_SHOW_LIGHTS;
                //设置通知为默认铃声LED提醒震动
                //notification.defaults = Notification.DEFAULT_ALL;
                manager.notify(2, notification);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //高 于API Level 11（Android 2.3.3），低于API Level 16 (Android 4.1.2)适用的创建通知方法
               NotificationManager manager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.drawable.p1)
                                .setContentTitle("This is title")
                                .setContentText("This is content")
                                .setWhen(System.currentTimeMillis())
                                .setTicker("TickerText:" + "您有新短消息，请注意查收！");
                manager.notify(1,builder.build());
            }
        });


    }
}
