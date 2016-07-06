package com.bjw.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "receiver in myself",Toast.LENGTH_SHORT).show();
        Logger.d("MyBroadcastReceiver");
    }
}
