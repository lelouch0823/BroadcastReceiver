package com.bjw.message;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAdd;
    private Button mDe;
    private Button mTongz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAdd = (Button) findViewById(R.id.add);
        mDe = (Button) findViewById(R.id.de);

        mAdd.setOnClickListener(this);
        mDe.setOnClickListener(this);
        mTongz = (Button) findViewById(R.id.tongz);
        mTongz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms");
        switch (v.getId()) {
            case R.id.add:
                ContentValues values = new ContentValues();
                values.put("address", "8888");
                values.put("date", System.currentTimeMillis());
                values.put("type", 1);
                values.put("body", "haha");
                resolver.insert(uri, values);
                break;
            case R.id.de:
                resolver.delete(uri, "address=?", new String[]{"8888"});
                break;
            case R.id.tongz:
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification1 = new Notification(R.mipmap.ic_launcher, "hahaah", System.currentTimeMillis());
                Intent intent = new Intent();
                intent.setData(Uri.parse("tel://112"));
                intent.setAction(Intent.ACTION_DIAL);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                Notification notification = new Notification.Builder(MainActivity.this)
                        .setContentText("Content")
                        .setContentTitle("Title")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .build();
                manager.notify(Notification.FLAG_AUTO_CANCEL, notification);
                break;
        }
    }
}
