package com.bjw.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private IntentFilter intentFilter ;
    private NetworkChangeReceiver networkChangeReceiver;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        //向IntentFilter中添加要接收的广播类型:网路变化
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //创建自定义广播实例
        networkChangeReceiver = new NetworkChangeReceiver();
        //使用Activity注册广播
        registerReceiver(networkChangeReceiver, intentFilter);
        //获取当前活动的LocalBroadcastManager的实例
        localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        button2 = (Button) findViewById(R.id.local_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.bjw.broadcastreceiver.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        intentFilter = new IntentFilter();
        //添加要接受的本地广播
        intentFilter.addAction("com.bjw.broadcastreceiver.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        //使用LocalBroadcastManager的
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.MY_BROADCAST");
                sendOrderedBroadcast(intent, null);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }


    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //使用Activity的getSystemService方法获取网路管理实例
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //通过ConnectivityManager的getActiveNetworkInfo方法获取网络信息NetworkInfo实例
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            //判断NetworkInfo是否为空及使用isAvailable方法判断当前是否有网络连接
            if (networkInfo != null && networkInfo.isAvailable()) {
                Logger.d("hell");
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            } else {
                Logger.d("hell");
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
                getSystemService(CONNECTIVITY_SERVICE);
            }

            //Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();
        }
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "This is localadcastreceiver", Toast.LENGTH_SHORT).show();
        }
    }
}
