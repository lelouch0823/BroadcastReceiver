package com.bjw.servicetest;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.start_service)
    Button startService;
    @BindView(R.id.stop_service)
    Button stopService;
    @BindView(R.id.bind_service)
    Button bindService;
    @BindView(R.id.unbind_service)
    Button unbindService;

    private Myservice.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (Myservice.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(1);
    }

    @OnClick({R.id.start_service, R.id.stop_service,R.id.bind_service, R.id.unbind_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                Intent startintent = new Intent(MainActivity.this, Myservice.class);
                startService(startintent);
                break;
            case R.id.stop_service:
                Intent stopintent = new Intent(MainActivity.this, Myservice.class);
                stopService(stopintent);
                break;
            case R.id.bind_service:
                Intent bindintent = new Intent(MainActivity.this, Myservice.class);
                bindService(bindintent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Intent unbindintent = new Intent(MainActivity.this, Myservice.class);
                unbindService(connection);
                break;
            default:
                break;
        }
    }
}
