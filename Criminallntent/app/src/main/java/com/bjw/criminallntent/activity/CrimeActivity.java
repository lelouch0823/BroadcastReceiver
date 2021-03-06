package com.bjw.criminallntent.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bjw.criminallntent.fragment.CrimeFragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID =
            "com.bjw.criminallntent.crime_id";
    //获取启动本活动的Intent的方法
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        return new CrimeFragment().newInstance(crimeId);
    }
}
