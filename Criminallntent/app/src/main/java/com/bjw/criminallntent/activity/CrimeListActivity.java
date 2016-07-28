package com.bjw.criminallntent.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bjw.criminallntent.Crime;
import com.bjw.criminallntent.R;
import com.bjw.criminallntent.fragment.CrimeFragment;
import com.bjw.criminallntent.fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{


    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager()
                    .beginTransaction()
                    //每次点击list条目替换Fragment
                    .replace(R.id.detail_fragment_container,newDetail)
                    .commit();

        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment =
                (CrimeListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
