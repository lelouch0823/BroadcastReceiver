package com.bjw.youkumenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class YoukuMenu extends FrameLayout implements View.OnClickListener {
    private RelativeLayout mRlLv3;
    private Button mBtnLv2;
    private RelativeLayout mRlLv2;
    private Button mBtnLv1;
    private RelativeLayout mRlLv1;
    private boolean mRlLv2IsShow =true;
    private boolean mRlLv3IsShow =true;


    public YoukuMenu(Context context) {
        super(context);
    }

    public YoukuMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.youku_menu, this);
        initView(view);
    }

    private void initView(View view) {
        mRlLv1 = (RelativeLayout) view.findViewById(R.id.rl_lv1);
        mRlLv2 = (RelativeLayout) view.findViewById(R.id.rl_lv2);
        mRlLv3 = (RelativeLayout) view.findViewById(R.id.rl_lv3);
        mBtnLv1 = (Button) view.findViewById(R.id.btn_lv1);
        mBtnLv2 = (Button) view.findViewById(R.id.btn_lv2);
        mBtnLv2.setOnClickListener(this);
        mBtnLv1.setOnClickListener(this);
    }

    public YoukuMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lv1:
                if (mAnimeCount != 0) {
                    return;
                }
                if (!mRlLv2IsShow) {
                    show(mRlLv2);
                } else if (mRlLv2IsShow){
                    hide(mRlLv2);
                    if (mRlLv3IsShow) {
                        hide(mRlLv3, 300);
                        mRlLv3IsShow = !mRlLv3IsShow;
                    }
                }
                mRlLv2IsShow = !mRlLv2IsShow;
                break;
            case R.id.btn_lv2:
                if (!mRlLv2IsShow) {
                    return;
                }
                if (mAnimeCount != 0) {
                    return;
                }
                if (!mRlLv3IsShow) {
                    show(mRlLv3);
                } else {
                    hide(mRlLv3);
                }
                mRlLv3IsShow = !mRlLv3IsShow;
                break;
        }
    }

    private void hide(View hide, long startOffset) {
        startRotateAnime(hide, 0f, -180f, startOffset);
    }

    private void hide(View hide) {
        float fromDegrees = 0f;
        float toDegrees = -180f;
        startRotateAnime(hide, fromDegrees, toDegrees, 0L);
        hide.setVisibility(GONE);
    }

    private void startRotateAnime(View view, float fromDegrees, float
            toDegrees, long startOffset) {
        RotateAnimation animation = new RotateAnimation(fromDegrees,
                toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 1.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setStartOffset(startOffset);
        animation.setAnimationListener(mRotateListener);
        view.startAnimation(animation);
    }

    private int mAnimeCount;
    private Animation.AnimationListener mRotateListener = new Animation
            .AnimationListener() {


        @Override
        public void onAnimationStart(Animation animation) {
            mAnimeCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mAnimeCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void show(View show) {
        show.setVisibility(VISIBLE);
        startRotateAnime(show,-180, 0, 0L);
    }
}
