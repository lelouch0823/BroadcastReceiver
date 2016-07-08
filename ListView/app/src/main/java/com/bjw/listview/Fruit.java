package com.bjw.listview;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class Fruit {
    private String name;
    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public Fruit(String name,int imageId) {

        this.imageId = imageId;
        this.name = name;
    }
}
