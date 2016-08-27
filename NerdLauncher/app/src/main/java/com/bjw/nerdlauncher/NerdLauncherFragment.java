package com.bjw.nerdlauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class NerdLauncherFragment extends Fragment {
    private static final String TAG = "NerdLauncherFragment";
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nerd_launcher,container,false);
         mRecyclerView = (RecyclerView) view.findViewById
                 (R.id.fragment_nerd_launcher_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager manager = getActivity().getPackageManager();
        List<ResolveInfo> activities = manager.queryIntentActivities(startupIntent, 0);
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo a, ResolveInfo b) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.loadLabel(pm).toString(),
                        b.loadLabel(pm).toString());
            }
        });
        Log.i(TAG, "Found" + activities.size());
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }

    public static NerdLauncherFragment newInstance() {
        Bundle args = new Bundle();

        NerdLauncherFragment fragment = new NerdLauncherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mIcon;
        private TextView mNameTextView;
        private ResolveInfo mInfo;

        public ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.app_name);
            mIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            mNameTextView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo info) {
            mInfo = info;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mInfo.loadLabel(pm).toString();
            Drawable drawable = mInfo.loadIcon(pm);
            mIcon.setImageDrawable(drawable);
            mNameTextView.setText(appName);
        }

        @Override
        public void onClick(View view) {
            ActivityInfo info = mInfo.activityInfo;
            Intent intent = new Intent(Intent.ACTION_MAIN)
                    .setClassName(info.applicationInfo.packageName,info.name);
            startActivity(intent);
        }
    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {

        private List<ResolveInfo> mActivities;

        public ActivityAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.nerd_launcher_list_item, parent, false);
            return new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            holder.bindActivity(mActivities.get(position));
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }

}
