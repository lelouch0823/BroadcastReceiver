package com.bjw.studentmessagesystem.activity;

import android.support.v4.app.Fragment;

import com.bjw.studentmessagesystem.Fragment.DataControlFragment;

public class DataControlActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DataControlFragment();
    }
}
