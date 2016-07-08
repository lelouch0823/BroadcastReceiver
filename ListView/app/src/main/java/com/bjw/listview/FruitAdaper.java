package com.bjw.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class FruitAdaper extends ArrayAdapter<Fruit> {
    private int resourceId;
    public FruitAdaper(Context context, int textViewResourceId, List<Fruit> fruits) {
        super(context, textViewResourceId, fruits);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = (Fruit) getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = (TextView)view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder =(ViewHolder) view.getTag();
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
/*        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());*/
        return view;
    }
    class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
    }
}
