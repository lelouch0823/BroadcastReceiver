package com.bjw.fragmentbestpractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

;import com.orhanobut.logger.Logger;

//import android.support.v4.app.FragmentActivity;

//import android.app.Fragment;



/**
 * Created by Administrator on 2016/7/3 0003.
 */
public class NewsContentActivity extends Activity {
    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_content);
        String newsTitle = getIntent().getStringExtra("new_title");
        Logger.d("hello");
        String newsContent = getIntent().getStringExtra("news_content");
        Logger.d("hello");

        NewsContentFragment newsContentFragment = (NewsContentFragment)getFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle, newsContent);

    }
}
