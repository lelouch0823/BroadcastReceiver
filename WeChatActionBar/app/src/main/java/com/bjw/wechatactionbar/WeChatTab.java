package com.bjw.wechatactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class WeChatTab extends View {
    private Paint mTextPaint;
    private Rect mTextBound;
    private int mTextSize;
    private float mAlpha;
    private int mColor = 0xFF45C01A;
    private String mText;
    private Bitmap mIconBitmap;
    private Rect mIconRect;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    public WeChatTab(Context context) {
        this(context, null);
    }

    public WeChatTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeChatTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WeChatTab);

        int icon = array.getResourceId(R.styleable.WeChatTab_icon, -1);
        if (icon != -1) {
            mIconBitmap = BitmapFactory.decodeResource(getResources(), icon);
        }
        int color = array.getColor(R.styleable.WeChatTab_view_color, -1);
        if (color != -1) {
            mColor = color;
        }
        mText = array.getString(R.styleable.WeChatTab_bottom_text);
        mTextSize = (int) array.getDimension(R.styleable.WeChatTab_text_size, 0);
        array.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0XFF555555);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());

        int left = (getMeasuredWidth() - iconWidth) / 2;
        int top = (getMeasuredHeight() / 2) - (mTextBound.height() + iconWidth) / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha);

        setUpTargetBitmap(alpha);

    }

    private void setUpTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config
                .ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(0xFF45C01A);
        mPaint.setAlpha(alpha);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }
}
