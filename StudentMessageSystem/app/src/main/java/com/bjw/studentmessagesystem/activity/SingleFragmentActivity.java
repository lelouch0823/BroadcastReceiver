package com.bjw.studentmessagesystem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bjw.studentmessagesystem.R;

/**
 * 需求:实现Activity托管Fragment的类
 * 分析:
 * 1.在onCreate方法内关联Fragment容器视图
 * 2.获取FragmentManage
 * 3.创建关联Fragment和id
 * 4.添加Fragment提交事务
 *
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager manager = getSupportFragmentManager();

        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }

    protected abstract Fragment createFragment();

}
