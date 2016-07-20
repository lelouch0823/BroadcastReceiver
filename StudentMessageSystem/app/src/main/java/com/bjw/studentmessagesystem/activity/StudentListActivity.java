package com.bjw.studentmessagesystem.activity;

import android.support.v4.app.Fragment;

import com.bjw.studentmessagesystem.Fragment.StudentListFragment;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class StudentListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new StudentListFragment();
    }
}
