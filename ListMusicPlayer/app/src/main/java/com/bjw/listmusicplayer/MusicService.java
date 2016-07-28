package com.bjw.listmusicplayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MusicService extends Service {
    private static final String PLAY_ACTION = "the action";
    private static final String MUSIC_POSTION = "this is the music";
    private static final int UPDATE_UI = 0;
    private MediaPlayer mMediaPlayer;
    public  String mPath;
    public  List<File> sMusices;
    private File mMusic;
    private int mPostion;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Intent newIntent(Context context, int postion, int action) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(PLAY_ACTION, action);
        intent.putExtra(MUSIC_POSTION, postion);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int action = intent.getIntExtra(PLAY_ACTION, -1);

        switch (action) {
            case 0:
                stop();
                mMediaPlayer = null;
                setMusic(intent);
                play();
                break;
            case 1:
                setMusic(intent);
                play();
                break;
            case 2:
                pause();
                break;
            case 3:
                stop();
                break;
            case 4:
                stop();
                mMediaPlayer = null;
                setMusic(intent);
                play();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void play() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    private void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    private void setMusic(Intent intent) {
        sMusices = MusicControlFragment.sFiles;
        Logger.d(sMusices.toString());
        int action = intent.getIntExtra(PLAY_ACTION, -1);
        mPostion = intent.getIntExtra(MUSIC_POSTION, -1);
        Logger.d(mPostion);
        if (action == -1 || mPostion == -1 || mPostion >= sMusices.size()) {
            Toast.makeText(this, "要播放的音乐不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        mMediaPlayer = new MediaPlayer();
        try {
            mMusic = sMusices.get(mPostion);
            mMediaPlayer.setDataSource(mMusic
                    .getAbsolutePath());
            mMediaPlayer.setLooping(false);
           mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(final MediaPlayer mediaPlayer) {
                    try {
                        mediaPlayer.reset();
                        if (mPostion == sMusices.size() - 1) {
                            Toast.makeText(MusicService.this, "全部歌曲播放完毕," +
                                    "已经没有下一首歌曲: " + mMusic.getName(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(MusicService.this, "本歌曲播放完毕," +
                                "开始播放下一首歌: " + mMusic.getName(), Toast.LENGTH_SHORT).show();
                        mPostion = mPostion + 1;
                        MusicControlFragment.mPostion = mPostion;
                        mediaPlayer.setDataSource(sMusices.get(mPostion).getAbsolutePath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = Message.obtain();
                                message.what = UPDATE_UI;
                                message.obj = mPostion;
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
 /*       Logger.d(sMusices.toString());
        if (mMediaPlayer.isPlaying()) {
            return;
        }
        mMediaPlayer = new MediaPlayer();
        try {
            mMusic = sMusices.get(mPostion);
            mMediaPlayer.setDataSource(mMusic
                    .getAbsolutePath());
            mMediaPlayer.setLooping(false);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    try {
                        if (mPostion == sMusices.size() - 1) {
                            Toast.makeText(MusicService.this, "本歌曲播放完毕," +
                                    "已经没有下一首歌曲: " + mMusic.getName(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(MusicService.this, "本歌曲播放完毕," +
                                "开始播放下一首歌: " + mMusic.getName(), Toast.LENGTH_SHORT).show();
                        mPostion = mPostion + 1;
                        mediaPlayer.setDataSource(sMusices.get(mPostion).getAbsolutePath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}
