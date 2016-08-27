package com.bjw.bindservicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bjw.anotherapp.MyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBindService;
    private Button mUnbindService;

    private MyService.DownloadBinder mIBinder;
    private Button mCallMethod;

    private MyAidlInterface mInterface;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //mIBinder = (MyService.DownloadBinder) iBinder;
            mInterface = MyAidlInterface.Stub.asInterface(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBindService = (Button) findViewById(R.id.bind_service);
        mUnbindService = (Button) findViewById(R.id.unbind_service);

        mBindService.setOnClickListener(this);
        mUnbindService.setOnClickListener(this);
        mCallMethod = (Button) findViewById(R.id.call_method);
        mCallMethod.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_service:
                Intent bindintent = new Intent();
                bindintent.setAction("com.bjw.anotherapp.service");
                bindintent.setPackage("com.bjw.anotherapp");
                bindService(bindintent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Intent intent1 = new Intent(this, MyService.class);
                unbindService(connection);
                break;
            case R.id.call_method:
                //mIBinder.callServiceMethod();
                try {
                    mInterface.startDownload();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
