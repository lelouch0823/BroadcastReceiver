package com.bjw.criminallntent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
//        Date date = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("EE-MM-dd-yyyy");
//        mDate = dateFormat.format(date);
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date  date) {
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
