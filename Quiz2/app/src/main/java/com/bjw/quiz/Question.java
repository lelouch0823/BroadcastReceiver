package com.bjw.quiz;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsCheat;

    public boolean getCheat() {
        return mIsCheat;
    }

    public void setCheat(boolean cheat) {
        mIsCheat = cheat;
    }

    public Question( int textResId,boolean answerTrue) {
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
    }

    public Question() {

    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean getAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
