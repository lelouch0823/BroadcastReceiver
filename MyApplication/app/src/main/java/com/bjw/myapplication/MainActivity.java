package com.bjw.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnShow;
    private ImageView mImgSrc;
    private ImageView mImgScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mImgSrc = (ImageView) findViewById(R.id.img_src);
        mImgScale = (ImageView) findViewById(R.id.img_scale);

        mBtnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                String path = "/mnt/sdcard/1.jpg";
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path,options);
                int srcheight = options.outHeight;
                int srcWidth = options.outWidth;
                int destHeight = getWindowManager().getDefaultDisplay().getHeight();
                int destWidth = getWindowManager().getDefaultDisplay().getWidth();
                int i = 1;
                if (srcheight > destHeight || srcWidth > srcheight) {
                    if (srcheight > srcWidth) {
                        i = Math.round(srcheight / destHeight);
                    } else {
                        i = Math.round(srcWidth / destWidth);
                    }
                }
                options = new BitmapFactory.Options();
                options.inSampleSize = i;
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                mImgSrc.setImageBitmap(bitmap);

                Bitmap copybitmap = Bitmap.createBitmap(bitmap.getWidth(),
                        bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(copybitmap);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                Matrix matrix = new Matrix();
                matrix.setScale(0.8f,0.8f);
                matrix.postRotate(180.0f,copybitmap.getWidth()/2,copybitmap.getHeight()/2);
                canvas.drawBitmap(bitmap,matrix,paint);
                mImgScale.setImageBitmap(copybitmap);

                break;
        }
    }
}
