package com.bjw.fragmentbestpractice;

/**
 * Created by Administrator on 2016/7/2 0002.
 */
public class News {
    private String title;
    private String content;
    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public News() {
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
