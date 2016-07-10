package com.bjw.choosepictest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;

    public static final int CROP_PHOTO = 2;

    private Uri imagerUri;

    private android.widget.Button takephoto;

    private android.widget.ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.picture = (ImageView) findViewById(R.id.picture);
        this.takephoto = (Button) findViewById(R.id.take_photo);
        picture.setOnClickListener(this);
        takephoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                //生成文件名为tempImage.jpg储存在SD卡内的照片的文件对象
                File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
                //判断文件是否已经存在
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取照片的地址值
                imagerUri = Uri.fromFile(outputImage);
                //为Intent添加启动相机的action
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //在Intent中放入照片输出的地址值
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imagerUri);
                //启动相机
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //为Intent添加打开裁剪程序的action
                    Intent intent = new Intent("com.android.camera.cation.CROP");
                    //设置文件类型
                    intent.setDataAndType(imagerUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imagerUri);
                    //启动裁剪程序
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (requestCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream
                                (getContentResolver().openInputStream(imagerUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
