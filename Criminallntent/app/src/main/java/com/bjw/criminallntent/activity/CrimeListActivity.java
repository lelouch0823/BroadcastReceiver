package com.bjw.criminallntent.activity;

import android.support.v4.app.Fragment;

import com.bjw.criminallntent.fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
