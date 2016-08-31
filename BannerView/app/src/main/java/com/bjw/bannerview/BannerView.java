package com.bjw.bannerview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class BannerView extends RelativeLayout {
    private ViewPager mVpBannerImg;
    private TextView mTvBannerTitle;
    private LinearLayout mLlDotsContainer;

    private String[] descs = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀",
    };

    private int[] mImages = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
    };
    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.banner_view, this);
        initView(view);
    }

    private void initView(View view) {
        mVpBannerImg = (ViewPager) view.findViewById(R.id.vp_banner_img);
        mTvBannerTitle = (TextView) view.findViewById(R.id.tv_banner_title);
        mLlDotsContainer = (LinearLayout) view.findViewById(R.id
                .ll_dots_container);
        setViewPager();
    }

    private void setViewPager() {
        mVpBannerImg.setAdapter(new BannerAdapter(mImages));
        mVpBannerImg.addOnPageChangeListener(new ViewPagerChangeListener());
        initDots();
    }

    public class ViewPagerChangeListener implements ViewPager
            .OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int
                positionOffsetPixels) {

        }


        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initDots() {
        for (int i = 0; i < mImages.length; i++) {
            int dp = dp2px(5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (dp, dp);
            params.leftMargin = dp;
            View dot = new View(getContext());
            dot.setLayoutParams(params);
            dot.setBackgroundResource(R.drawable.selector_dots);
            mLlDotsContainer.addView(dot);
        }
    }

    private int dp2px(int i) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (i * density + 0.5f);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
