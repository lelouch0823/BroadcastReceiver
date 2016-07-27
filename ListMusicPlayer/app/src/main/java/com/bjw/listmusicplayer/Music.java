package com.bjw.listmusicplayer;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class Music {
    public static Music sMusic;

    private List<File> mMusics;

    public Music(Context context,File file) {
        mMusics = getMusicList(file);
    }

    public List<File> getMusics () {
        return mMusics;
    }

    public static Music get(Context context,File file) {
        if (sMusic == null) {
            sMusic = new Music(context,file);
        }
        return sMusic;
    }

    public static List<File> getMusicList(File file) {
        List<File> pathList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File music : files) {
            if (music.isFile() && music.getName().endsWith("mp3")) {
                pathList.add(music);
            }
        }
        Log.d("dd", pathList.toString());
        return pathList;
    }
}
