package com.crazy.toutiaonews.newsfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.crazy.toutiaonews.adapterofall.MyAdapter;
import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.utils.JsonCache;
import com.crazy.toutiaonews.utils.NetState;
import com.crazy.toutiaonews.utils.Utils;
import com.scxh.slider.library.SliderLayout;
import com.warmtel.android.xlistview.XListView;


public class YuLeFragment extends Fragment {


    private String url = "http://c.m.163.com/nc/article/list/T1348648517839/0-20.html";
    private SliderLayout sliderLayout;

    public static YuLeFragment newInstance() {
        YuLeFragment fragment = new YuLeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yu_le, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sliderLayout !=null) {
            sliderLayout.stopAutoCycle();
            sliderLayout = null;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ProgressBar progressBar = (ProgressBar)getView().findViewById(R.id.news_progressbar);
        XListView listView = (XListView)getView().findViewById(R.id.yule_main_list_view);
        MyAdapter adapter = new MyAdapter(getActivity());

        Utils utils = new Utils(getActivity());

        listView.setAdapter(adapter);

        listView.setEmptyView(progressBar);
        utils.getAsyncDataList(url, adapter, listView, 1);
        utils.clickForXListView(url, adapter, listView, 1);

    }

}
