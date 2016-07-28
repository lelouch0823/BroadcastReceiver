package com.bjw.listmusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MusicControlFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_FILE = 0;
    private static final String PLAY_MUSIC = "the music want to play";
    private EditText mEtFilePath;
    private Button mBtPrevMusic;
    private Button mBtPlayMusic;
    private Button mBtStopMusic;
    private Button mBtNextMusic;
    private File mMusicFolder;
    private ImageButton mBtListMusic;
    //private File mMusic;
    public static int  mPostion;
    private Intent mServiceIntent;
    public static List<File> sFiles;
    private int mIsplaying;
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_control, container, false);
        initView(view);
        mIsplaying = 1;
        return view;
    }


    private void initView(View view) {
        mEtFilePath = (EditText) view.findViewById(R.id.et_file_path);
        mBtPrevMusic = (Button) view.findViewById(R.id.bt_prev_music);
        mBtPlayMusic = (Button) view.findViewById(R.id.bt_play_music);
        mBtStopMusic = (Button) view.findViewById(R.id.bt_stop_music);
        mBtNextMusic = (Button) view.findViewById(R.id.bt_next_music);
        mBtListMusic = (ImageButton) view.findViewById(R.id.bt_list_music);
        mBtPrevMusic.setOnClickListener(this);
        mBtPlayMusic.setOnClickListener(this);
        mBtStopMusic.setOnClickListener(this);
        mBtNextMusic.setOnClickListener(this);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            mPostion = intent.getIntExtra(PLAY_MUSIC, -1);
        }
        mBtListMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
                if (mMusicFolder == null) {
                    Toast.makeText(getContext(), "请输入包含音乐的文件夹", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = MusicListActivity.newIntent(mMusicFolder, getActivity());
                startActivityForResult(intent, REQUEST_FILE);
            }
        });
        mTv = (TextView) view.findViewById(R.id.tv_playing_music);
    }

    public static Intent newIntent(FragmentActivity activity, int position) {
        Intent intent = new Intent(activity, MusicControlActivity.class);
        intent.putExtra(PLAY_MUSIC, position);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_FILE && data != null) {

        }
    }

    @Override
    public void onClick(View v) {
        submit();
        switch (v.getId()) {
            case R.id.bt_prev_music:
                if (mPostion == 0) {
                    Toast.makeText(getActivity(), "当前为第一首歌", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPostion = --mPostion;
                Logger.d(mPostion);
                starService(mPostion, 0);
                mTv.setText("正在播放" + sFiles.get(mPostion).getName());
                mTv.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_play_music:
                starService(mPostion, mIsplaying);
                if (mIsplaying != 2) {
                    mIsplaying = 2;
                    mBtPlayMusic.setText("暂停");
                    mTv.setVisibility(View.VISIBLE);
                    mTv.setText("正在播放" + sFiles.get(mPostion).getName());
                } else {
                    mIsplaying = 1;
                    mBtPlayMusic.setText("播放");
                    mTv.setText("暂停播放");
                    mTv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_stop_music:
                starService(mPostion, 3);
                mIsplaying = 1;
                mBtPlayMusic.setText("播放");
                mTv.setVisibility(View.INVISIBLE);
                break;
            case R.id.bt_next_music:
                if (mPostion == sFiles.size() - 1) {
                    Toast.makeText(getActivity(), "当前为最后一首歌", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPostion = mPostion + 1;
                Logger.d(mPostion);
                starService(mPostion, 4);
                mTv.setText("正在播放" + sFiles.get(mPostion).getName());
                mTv.setVisibility(View.VISIBLE);
                break;
        }

    }

    public void starService(int postion, int action) {
        mServiceIntent = MusicService.newIntent(getActivity(), postion, action);
        getActivity().startService(mServiceIntent);
    }

    private void submit() {
        String path = mEtFilePath.getText().toString().trim();
        mMusicFolder = new File(path);
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(getContext(), "请输入包含音乐的文件夹", Toast.LENGTH_SHORT).show();
            return;
        } else if (!mMusicFolder.exists() || mMusicFolder.isFile()) {
            Toast.makeText(getActivity(), "输入的文件夹错误,请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        sFiles = Music.get(getActivity(), mMusicFolder).getMusics();
    }

}
