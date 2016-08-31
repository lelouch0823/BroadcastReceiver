package com.bjw.mylistdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class MyListView extends ListView {

    private ImageView mImageArrow;
    private ProgressBar mPbLoading;
    private TextView mTvRefreshMessage;
    private TextView mTvRefreshTime;
    private final View mHeaderView;
    private int mHeaderHeight;
    private int mDownY;
    private static final int STATE_PULL_TO_REFRESH = 0;
    /**
     * 状态：松开刷新
     */
    private static final int STATE_RELEASE_REFRESH = 1;
    /**
     * 状态：正在刷新
     */
    private static final int STATE_REFRESHING = 2;
    /**
     * 当前状态
     */
    private int mCurrentState = STATE_PULL_TO_REFRESH;    // 默认是下拉刷新状态
    private RefreshingListener mRefreshingListener;

    /**
     * Instantiates a new My list view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeaderView = View.inflate(context, R.layout.list_header_view, null);
        initHeaderView(mHeaderView);
        addHeaderView(mHeaderView);
        hideHeader();
    }

    private void hideHeader() {
        mHeaderView.measure(0, 0);
        mHeaderHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
    }

    private void showHeader() {
        int paddingTop = 0;
        setFooterViewPadding(paddingTop);
    }

    private void setFooterViewPadding(int paddingTop) {
        mHeaderView.setPadding(0, paddingTop, 0, 0);
    }

    private void initHeaderView(View view) {
        mImageArrow = (ImageView) view.findViewById(R.id.image_arrow);
        mPbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
        mTvRefreshMessage = (TextView) view.findViewById(R.id
                .tv_refresh_message);
        //mTvRefreshTime = (TextView) view.findViewById(R.id.tv_refresh_time);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int firstVisiblePosition = getFirstVisiblePosition();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentState == STATE_REFRESHING) {
                    return super.onTouchEvent(ev);
                }
                int moveY = (int) (ev.getY() - mDownY);
                if (firstVisiblePosition == 0 && moveY > 0) {
                    int paddingTop = -mHeaderHeight + moveY;
                    setFooterViewPadding(paddingTop);
                    if (paddingTop < 0 && mCurrentState !=
                            STATE_PULL_TO_REFRESH) {
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        RotateArrow(0, 180);
                    } else if (paddingTop > 0 && mCurrentState !=
                            STATE_RELEASE_REFRESH) {
                        mCurrentState = STATE_RELEASE_REFRESH;
                        mTvRefreshMessage.setText("松开刷新");
                        RotateArrow(180, 360);
                    }
                    return true;

                }
                break;
            case MotionEvent.ACTION_UP:
                int upY = (int) (ev.getY() - mDownY);
                if (firstVisiblePosition == 0 && upY > mHeaderHeight) {
                    showHeader();
                    mCurrentState = STATE_REFRESHING;
                    mTvRefreshMessage.setText("正在刷新数据");
                    showProgressBar(true);
                    if (mRefreshingListener != null) {
                        mRefreshingListener.onRefreshing();
                    }
                    return true;
                } else if (mCurrentState != STATE_RELEASE_REFRESH) {
                    hideHeader();
                }
                break;
            default:

                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * Sets on refreshing listener.
     *
     * @param listener the listener
     */
    public void setOnRefreshingListener(RefreshingListener listener) {
        mRefreshingListener = listener;
    }

    /**
     * The interface Refreshing listener.
     */
    public interface RefreshingListener {
        /**
         * On refreshing.
         */
        void onRefreshing();
    }

    private void showProgressBar(boolean show) {
        if (show) {
            mImageArrow.clearAnimation();
            mImageArrow.setVisibility(GONE);
            mPbLoading.setVisibility(VISIBLE);
        } else {
            mPbLoading.setVisibility(GONE);
            mImageArrow.setVisibility(VISIBLE);
            hideHeader();
        }
    }

    private void RotateArrow(float from, float to) {
        RotateAnimation rt = new RotateAnimation(from, to,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rt.setDuration(200);
        rt.setFillAfter(true);
        mImageArrow.startAnimation(rt);
    }

    /**
     * Refresh complete.
     */
    public void RefreshComplete() {
        showProgressBar(false);
        mCurrentState = STATE_PULL_TO_REFRESH;
    }
}
