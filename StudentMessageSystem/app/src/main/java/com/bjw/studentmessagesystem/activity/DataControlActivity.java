package com.bjw.studentmessagesystem.activity;

import android.support.v4.app.Fragment;

import com.bjw.studentmessagesystem.Fragment.DataControlFragment;
import com.orhanobut.logger.Logger;

public class DataControlActivity extends SingleFragmentActivity {


    @Override
    public void onPause() {
        super.onPause();
        Logger.d("d");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("d");
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        Logger.d("a");
    }

    @Override
    protected Fragment createFragment() {
        return new DataControlFragment();
    }
}
