package com.bjw.listmusicplayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

public class MusicControlActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new MusicControlFragment();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提醒:");
        builder.setMessage("是否在后台继续播放音乐?");
        builder.setPositiveButton("继续播放", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("停止播放", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MusicControlActivity.this,MusicService.class);
                stopService(intent);
                finish();
            }
        });
        builder.show();
    }
}
