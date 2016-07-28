package com.bjw.anotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.Logger;


/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class TheService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends MyAidlInterface.Stub {
        public void startDownload() {
            Logger.d("开始下载");
        }

        public int getProgress() {
            return 0;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("dd", "dddddddd");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("dd", "dddddddd");
    }
}
