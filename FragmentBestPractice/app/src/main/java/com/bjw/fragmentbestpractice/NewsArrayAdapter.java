package com.bjw.fragmentbestpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3 0003.
 */
public class NewsArrayAdapter extends ArrayAdapter{
    private int resourceId;
    public NewsArrayAdapter(Context context, int textViewResource, List<News> objects) {
        super(context, textViewResource, objects);
        this.resourceId = textViewResource;
    }

    @Override
    //
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = (News) getItem(position);
        View view;
        //ViewHolder用来储存View
        ViewHolder viewHolder;
        //判断传入的View是否为空
        if (convertView == null) {
            //为空的加载布局
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            //将创建的View赋给viewHolder中的TextView newsTitleText
            viewHolder.newsTitleText = (TextView) view.findViewById(R.id.news_title);
            //把viewHolder存到view中
            view.setTag(viewHolder);
        } else {
            view = convertView;
            //取出view赋值给viewHolder;
            viewHolder = (ViewHolder) view.getTag();
        }
        //给储存在ViewHolder中的TextView设置显示内容
        viewHolder.newsTitleText.setText(news.getTitle());

        return view;
    }
    class ViewHolder {
        TextView newsTitleText;
    }
}
