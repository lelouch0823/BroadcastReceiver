package com.bjw.fragmentbestpractice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/3 0003.
 */
public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView newsTitleListView;
    private List<News> newsList;
    private NewsArrayAdapter adapter;
    private boolean isTwoPane;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        newsList = getNews();
        adapter = new NewsArrayAdapter(activity, R.layout.news_item, newsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        newsTitleListView = (ListView) view.findViewById(R.id.news_title_list_view);
        newsTitleListView.setAdapter(adapter);
        newsTitleListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }


    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News("正在看此书的人说下自己的感受：","大一。"));
        newsList.add(new News("专注于移动互联网的Geek和Blogger","Intellij IDEA的Code Inspections，也安全的结构来提高效率之类的都有提示。你可以设置是否做某项检查以及提示错误的等级，你还可以定义自不只是嘴上说说~"));
        return newsList;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        News news = newsList.get(i);
        if (isTwoPane) {
            NewsContentFragment newsContentFragment =
                    (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(),news.getContent());
        } else {
            NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
        }
    }
}
