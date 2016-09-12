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
        //获取第一个子View也就是侧边菜单
        mMenu = getChildAt(0);
        //回去第二个子View主界面
        mMain = getChildAt(1);
        //获取菜单View在
        mMenuWidth = mMenu.getLayoutParams().width;
        //定义菜单View的测量标准
        int menuWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mMenuWidth, MeasureSpec.EXACTLY);
        //测量菜单
        mMenu.measure(menuWidthMeasureSpec, heightMeasureSpec);
        //测了主界面
        mMain.measure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     *封装系统的scrollTo方法将x轴需平移的量参数取反传入
     *
     * @param x x轴需平移的量
     */
    public void scrollTo(int x) {
        super.scrollTo(-x, 0);
    }


    /**
     * 封装系统的getScrollX方法获取x轴的平移的位置取反返回
     *
     * @return the my scroll
     */
    public int getMyScroll() {
        return -getScrollX();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.i("接受到触摸事件");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //监听到手指按下的x坐标
                mDownX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //在手指抬起时获取平移的x
                int movedX = getMyScroll();
//                如果平移量大于菜单的宽度的一半显示整个菜单
                if (movedX > mMenuWidth / 2) {
                    startScroll(mMenuWidth);
                    //完全隐藏菜单
                } else {
                    startScroll(0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //获取x轴要平移的坐标，当前坐标减去手指按下时的坐标加上上次移动时的坐标
                int x = (int) event.getX();
                int moveX = x - mDownX + mCurrentX;
                Logger.i(x +"移动了"+moveX);
                //平移量小于0把要移动的量赋值为0，防止在已经完全隐藏了将菜单的情况下还会继续往左平移
                if (moveX < 0) {
                    moveX = 0;
                    //平移量大于菜单宽度时把移动的量赋值为菜单宽，
                    // 防止在已经完全显示了将菜单的情况下还会继续往右平移
                } else if (moveX > mMenuWidth) {
                    moveX = mMenuWidth;
                }
                //调用方法将整个界面平移
                scrollTo(moveX);
                break;
            default:
                break;
        }
        return true;
    }


    /**
     *自定义的缓慢平移方法
     *
     * @param xTo 要缓慢平移到哪个x坐标
     */
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

