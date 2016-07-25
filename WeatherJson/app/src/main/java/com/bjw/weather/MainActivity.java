package com.bjw.weather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NO_RESPONSE = 1;
    private static final int GET_DATA = 2;
    private EditText mCityName;
    private Button mGetWeather;
    private TextView mDay1;
    private TextView mDay2;
    private TextView mDay3;
    private ProgressDialog mDialog;
    private   String path = "http://wthrcdn.etouch.cn/weather_mini?city=";
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDialog.dismiss();
            switch (msg.what) {
                case NO_RESPONSE:
                    Toast.makeText(MainActivity.this, "无法获取该城市天气信息", Toast.LENGTH_SHORT).show();
                    break;
                case GET_DATA:
                    JSONArray jsonArray = (JSONArray) msg.obj;
                    try {
                        mDay1.setText(jsonArray.getJSONObject(0).toString());
                        mDay2.setText(jsonArray.getJSONObject(1).toString());
                        mDay3.setText(jsonArray.getJSONObject(2).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mCityName = (EditText) findViewById(R.id.city_name);
        mGetWeather = (Button) findViewById(R.id.get_weather);
        mDay1 = (TextView) findViewById(R.id.day1);
        mDay2 = (TextView) findViewById(R.id.day2);
        mDay3 = (TextView) findViewById(R.id.day3);

        mGetWeather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_weather:
                //Log.d("dd", "dd");
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        //final String name = mCityName.getText().toString().trim();

        try {
            path = "http://wthrcdn.etouch.cn/weather_mini?city="+ URLEncoder.encode("杭州","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "输入错误请重新输入", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("正在加载数据,请稍候...");
            mDialog.show();
            new Thread(){
                @Override
                public void run() {
                    try {
                        //path = path + URLEncoder.encode(name, "utf-8");
                        URL url = new URL(path);
                        HttpURLConnection connection =
                                (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        int responseCode = connection.getResponseCode();

                        if (responseCode == 200) {
                            Log.d("dd", "dd");
                            InputStream inputStream = connection.getInputStream();
                            BufferedReader reader = new BufferedReader
                                    (new InputStreamReader(inputStream));
                            StringBuilder builder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                builder.append(line);
                            }
                            Log.d("dd", builder.toString());
                            JSONObject jsonObject = new JSONObject(builder.toString());
                            String result = jsonObject.getString("desc");
                            if ("OK".equals(result)) {
                                Log.d("dd", "cc");
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = data.getJSONArray("forecast");
                                Message message = Message.obtain();
                                message.what = GET_DATA;
                                message.obj = jsonArray;
                                mHandler.sendMessage(message);
                            }
                        } else {
                            Message message = Message.obtain();
                            mHandler.sendEmptyMessage(NO_RESPONSE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message message = Message.obtain();
                        mHandler.sendEmptyMessage(NO_RESPONSE);
                    }
                }
            }.start();
        }
    }
}
