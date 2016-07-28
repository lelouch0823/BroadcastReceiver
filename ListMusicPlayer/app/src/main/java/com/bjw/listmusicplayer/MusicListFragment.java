package com.bjw.listmusicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MusicListFragment extends Fragment {
    public static List<File > sFiles;
    private RecyclerView mRecyclerView;
    private MusicListAdapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container,false);
        File file = (File) getActivity().getIntent()
                .getSerializableExtra(MusicListActivity.MUSIC_FILE);
        sFiles = Music.get(getActivity(),file).getMusics();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.music_list_fragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MusicListAdapter(sFiles);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    class MusicListViewHodler extends RecyclerView.ViewHolder {
        private int mPosition;
        private TextView mTv;
        public MusicListViewHodler(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = MusicControlFragment.newIntent(getActivity(),mPosition);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }

        public void bindMusic(File music, int position) {
            mPosition = position;
            mTv.setText(music.getName());
        }
    }

    class MusicListAdapter extends RecyclerView.Adapter<MusicListViewHodler> {
        private List<File> mMusics;
        public MusicListAdapter(List<File> music) {
            mMusics = music;
        }

        @Override
        public MusicListViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item, parent,false);
            return new MusicListViewHodler(view);
        }

        @Override
        public void onBindViewHolder(MusicListViewHodler holder, int position) {
            File music = mMusics.get(position);
            holder.bindMusic(music,position);
        }


        @Override
        public int getItemCount() {
            return mMusics.size();
        }
    }

}
