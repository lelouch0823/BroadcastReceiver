package com.bjw.beatbox;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutReId());
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager
                .findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutReId() {
        return R.layout.activity_fragment;
    }
}
