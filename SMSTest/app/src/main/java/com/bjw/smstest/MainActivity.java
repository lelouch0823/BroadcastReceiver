package com.bjw.smstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private android.widget.TextView sender;
    private android.widget.TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.content = (TextView) findViewById(R.id.content);
        this.sender = (TextView) findViewById(R.id.sender);
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String format = intent.getStringExtra("format");
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                byte[] pdu = (byte[])pdus[i];
                SmsMessage.createFromPdu(pdu, format);
            }
        }
    }
}
