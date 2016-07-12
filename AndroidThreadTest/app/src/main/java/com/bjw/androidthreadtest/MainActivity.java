package com.bjw.androidthreadtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final int UPDATE_TEXT = 1;

    private Handler handler = new Handler() {
        public void handleMessage (Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    text.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.change_text)
    Button changeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.change_text)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
        }
    }
}
