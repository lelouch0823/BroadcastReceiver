package com.bjw.mypaint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View mRed;
    private View mGreen;
    private View mBlue;
    private View mYellow;
    private View mPurple;
    private SeekBar mSeekbar;
    private ImageView mIv;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_save) {
            try {
                File file = new File(Environment.getExternalStorageDirectory(),
                        SystemClock.currentThreadTimeMillis() + ".jpg");
                FileOutputStream out = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.JPEG,
                        100, out);
                out.close();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
                intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
                sendBroadcast(intent);
                Toast.makeText(this, "hah", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBitmap = Bitmap.createBitmap(320, 320, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mCanvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLACK);
        initView();
    }

    private void initView() {
        mRed = (View) findViewById(R.id.red);
        mGreen = (View) findViewById(R.id.green);
        mBlue = (View) findViewById(R.id.blue);
        mYellow = (View) findViewById(R.id.yellow);
        mPurple = (View) findViewById(R.id.purple);
        mSeekbar = (SeekBar) findViewById(R.id.seekbar);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPaint.setStrokeWidth(seekBar.getProgress());
            }
        });
        mIv = (ImageView) findViewById(R.id.iv);
        mIv.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) motionEvent.getX();
                        startY = (int) motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int newX = (int) motionEvent.getX();
                        int newY = (int) motionEvent.getY();
                        mCanvas.drawLine(startX,startY,newX,newY,mPaint);
                        mIv.setImageBitmap(mBitmap);
                        startX = (int) motionEvent.getX();
                        startY = (int) motionEvent.getY();
                }
                return true;
            }
        });
        mRed.setOnClickListener(this);
        mGreen.setOnClickListener(this);
        mBlue.setOnClickListener(this);
        mYellow.setOnClickListener(this);
        mPurple.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red:
                mPaint.setColor(Color.RED);
                break;
            case R.id.green:
                mPaint.setColor(Color.GREEN);
                break;
            case R.id.blue:
                mPaint.setColor(Color.BLUE);
                break;
            case R.id.yellow:
                mPaint.setColor(Color.YELLOW);
                break;
            case R.id.purple:
                mPaint.setColor(0xffff00ff);
                break;
        }
    }
}
