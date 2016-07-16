package com.bjw.criminallntent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private String mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EE-MM-dd-yyyy");
        mDate = dateFormat.format(date);
    }

    public UUID getId() {
        return mId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String  date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
