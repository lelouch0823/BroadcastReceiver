package com.bjw.listmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MusicListActivity extends SingleFragmentActivity {
    public static final String MUSIC_FILE = "the folder of music";

    @Override
    protected Fragment createFragment() {
        return new MusicListFragment();
    }

    public static Intent newIntent(File file, Context context) {
        Intent intent = new Intent(context, MusicListActivity.class);
        intent.putExtra(MUSIC_FILE, file);
        return intent;
    }
}
