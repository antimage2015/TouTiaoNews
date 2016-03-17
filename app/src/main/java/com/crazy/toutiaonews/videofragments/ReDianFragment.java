package com.crazy.toutiaonews.videofragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.adapterofall.VideoAdapter;
import com.crazy.toutiaonews.utils.UtilsOfVideo;
import com.loopj.android.http.AsyncHttpClient;
import com.warmtel.android.xlistview.XListView;


public class ReDianFragment extends Fragment {

    // 热点视频新闻的 url
    private String url = "http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/0-10.html";

    private MediaPlayer mPlayer;
    private int pos;

    public static ReDianFragment newInstance() {
        ReDianFragment fragment = new ReDianFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_re_dian_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mPlayer = new MediaPlayer();
        // 获取窗口管理器
        WindowManager wManager = getActivity().getWindowManager();

        ProgressBar progressBar = (ProgressBar)getView().findViewById(R.id.video_progressbar);
        XListView listView = (XListView)getView().findViewById(R.id.redian_video_list_view);

        VideoAdapter adapter = new VideoAdapter(getActivity(), mPlayer, pos, wManager);
        listView.setAdapter(adapter);

        UtilsOfVideo uov = new UtilsOfVideo(getActivity());
        uov.getAsyncDataList(url, adapter, 0);
        listView.setEmptyView(progressBar);
        uov.clickVideoForXListView(url, adapter, 0, listView, mPlayer);
    }

    // 当其他Activity被打开时，暂停播放
    @Override
    public void onPause() {
        if (mPlayer.isPlaying()) {
            // 保存当前的播放位置
            pos = mPlayer.getCurrentPosition();
            mPlayer.stop();
        }
        super.onPause();
    }


    @Override
    public void onDestroy() {
        // 停止播放
        if (mPlayer.isPlaying()) mPlayer.stop();
        // 释放资源
        mPlayer.release();
        super.onDestroy();
    }
}
