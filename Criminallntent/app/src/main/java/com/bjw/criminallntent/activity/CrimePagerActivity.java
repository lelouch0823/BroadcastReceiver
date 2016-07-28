package com.bjw.criminallntent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bjw.criminallntent.Crime;
import com.bjw.criminallntent.CrimeLab;
import com.bjw.criminallntent.R;
import com.bjw.criminallntent.fragment.CrimeFragment;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks{

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    //private static final int RESULT_POSTION = 15;
    public static final String CRIME_POSTION = "this is postion";
    private static final String EXTRA_CRIME_ID =
            "com.bjw.criminallntent.crime_id";

    //获取启动本活动的Intent的方法
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager manager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        //遍历集合获取id相同的元素的位置
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                //设置mViewPager显示为传递过来的crime
                mViewPager.setCurrentItem(i);
                Intent intent = new Intent();
                intent.putExtra(CRIME_POSTION, i);
                setResult(RESULT_OK,intent);
            }
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
}
