package com.bjw.mylistdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyListView mLvList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mLvList = (MyListView) findViewById(R.id.lv_list);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("哈哈哈哈哈哈哈哈哈哈哈哈哈哈"+ i);
        }
        mAdapter = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, list);
        mLvList.setAdapter(mAdapter);
        mLvList.setOnRefreshingListener(new MyListView.RefreshingListener() {
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("刷新了" + System.currentTimeMillis());
                        mLvList.RefreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 3000);
            }
        });
    }
}
