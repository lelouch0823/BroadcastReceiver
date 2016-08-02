package com.bjw.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class BeatBoxFragment extends Fragment {

    private BeatBox mBeatBox;


    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate
                (R.layout.frament_beat_box, container, false);
        RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.fragment_beat_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return view;
    }

    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button mButton;
        private Sound mSound;

        public SoundHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_sound,container,false));
            mButton = (Button) itemView.findViewById(R.id.list_item_sound_button);

        }

        public void bindSound(Sound sound) {
            mSound = sound;
            mButton.setText(mSound.getName());
            mButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mBeatBox.play(mSound);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
        private List<Sound> mSounds;

        private SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }
        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            holder.bindSound(mSounds.get(position));
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
