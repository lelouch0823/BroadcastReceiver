package com.bjw.criminallntent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.bjw.criminallntent.Crime;
import com.bjw.criminallntent.R;
import com.bjw.criminallntent.fragment.dialog.TimePickerFragment;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class TimePickerActivity extends FragmentActivity{
    private Date mDate;

    private Crime mCrime;

    private static final String EXTRA_CRIME
            = "com.bjw.criminallntent.crime";


    public static Intent newIntent(Context packageContext, Date crimeId) {
        Intent intent = new Intent(packageContext, TimePickerActivity.class);
        intent.putExtra(EXTRA_CRIME, crimeId);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocker_time);
        mDate = (Date) getIntent().getSerializableExtra(EXTRA_CRIME);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.crime_time_set);
        if (fragment == null) {
            fragment = TimePickerFragment
                    .newInstance(mDate);
        }
        manager.beginTransaction()
                .add(R.id.crime_time_set, fragment)
                .commit();
    }


}
