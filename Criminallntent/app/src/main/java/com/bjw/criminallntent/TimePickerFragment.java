package com.bjw.criminallntent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.orhanobut.logger.Logger;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class TimePickerFragment extends Fragment {
    private Date mCrimeDate;
    private static final String ARG_TIME =
            "com.bjw.criminallntent.time";

    private TimePicker mCrimeTimeSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimeDate = (Date) getArguments().getSerializable(ARG_TIME);

    }

    public static TimePickerFragment newInstance(Date date) {
        Bundle arg = new Bundle();
        arg.putSerializable(ARG_TIME,date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(arg);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pocker_time, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mCrimeTimeSet = (TimePicker) view.findViewById(R.id.time);
        mCrimeTimeSet.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                Logger.i(i+"--"+i1);
                //mCrimeDate.setTime();
            }
        });

        /*TimePicker timePicker = new TimePicker(getActivity());
        timePicker.show*/
    }
}
