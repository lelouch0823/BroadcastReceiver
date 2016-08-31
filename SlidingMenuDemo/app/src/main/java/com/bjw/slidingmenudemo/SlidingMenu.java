package com.bjw.slidingmenudemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class SlidingMenu extends ViewGroup {
    private View mMenu;
    private View mMain;
    private int mMenuWidth;
    private int mCurrentX;
    private Scroller mScroller;
    private int mDownX;
    private int mDownY;

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mMain.layout(0, 0, r - l, b - t);
        mMenu.layout(-mMenuWidth, 0, 0, b - t);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenu = getChildAt(0);
        mMain = getChildAt(1);
        mMenuWidth = mMenu.getLayoutParams().width;
        int menuWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mMenuWidth,
                MeasureSpec.EXACTLY);
        mMenu.measure(menuWidthMeasureSpec, heightMeasureSpec);
        mMain.measure(widthMeasureSpec, heightMeasureSpec);
    }

    public void scrollTo(int x) {
        super.scrollTo(-x, 0);
    }

    public int getMyScroll() {
        return -getScrollX();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.i("接受到触摸事件");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                int movedX = getMyScroll();
                if (movedX > mMenuWidth / 2) {
                    startScroll(mMenuWidth);
                } else {
                    startScroll(0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.i("移动了");
                int moveX = (int) event.getX() - mDownX + mCurrentX;
                if (moveX < 0) {
                    moveX = 0;
                } else if (moveX > mMenuWidth) {
                    moveX = mMenuWidth;
                }
                scrollTo(moveX);
                break;
            default:
                break;
        }
        return true;
    }

    public void startScroll(int xTo) {
        mCurrentX = xTo;
        int distatnceX = xTo - getMyScroll();
        mScroller.startScroll(getMyScroll(), 0, distatnceX, 0, 800);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX = Math.abs((int) ev.getX() - mDownX);
                int distanceY = Math.abs((int) ev.getY() - mDownY);
                if (distanceX > distanceY) {
                    return true;
                }
                break;
            default:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}

