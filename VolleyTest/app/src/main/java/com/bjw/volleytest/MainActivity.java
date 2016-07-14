package com.bjw.volleytest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String text = null;
    private TextView mResponseText;
    private TextView responsetext;
    private android.widget.ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.image = (ImageView) findViewById(R.id.image);
        initView();
    }

    private void initView() {
        mResponseText = (TextView) findViewById(R.id.response_text);
        //getText();
        //getJason();
        //setImage();
        setImageLoader();
    }

    private void setImageLoader() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(image,
                R.drawable.ic_assignment_returned_black_18dp,
                R.drawable.ic_bug_report_black_18dp);
        imageLoader.get("http://img3.imgtn.bdimg.com/it/u=4258580863,3498117120&fm=21&gp=0.jpg", listener);
    }

    private void setImage() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest
                ("http://img3.imgtn.bdimg.com/it/u=4258580863,3498117120&fm=21&gp=0.jpg",
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                image.setImageBitmap(response);
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d(error.getMessage());
                    }
                });
        requestQueue.add(imageRequest);
    }

    private void getJason() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                ("http://v.juhe.cn/weather/index?cityname=hangzhou&key=***a7558b2e0bedaa19673f74a6809ce",
                        null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.e(error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void getText() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger.d(response);
                mResponseText.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.d(error);
            }
        });
        requestQueue.add(stringRequest);
    }


}
