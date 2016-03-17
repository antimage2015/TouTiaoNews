package com.crazy.toutiaonews.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.Toast;

import com.crazy.toutiaonews.adapterofall.VideoAdapter;
import com.crazy.toutiaonews.videonewsparsejson.VideoGaoXiaoT;
import com.crazy.toutiaonews.videonewsparsejson.VideoStrT;
import com.crazy.toutiaonews.videonewsparsejson.VideoTotalT;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.warmtel.android.xlistview.XListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 *  Created by scxh on 2016/1/14.
 */
public class UtilsOfVideo {

    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private Context context;
    private boolean isFirst = true;
    private boolean isRefresh = true;
    private String str;

    public UtilsOfVideo(Context context) {
        this.context = context;
    }

    public void clickVideoForXListView(final String url, final VideoAdapter adapter,
                                 final int position,final XListView listView,
                                       final MediaPlayer mPlayer) {
        // 默认是 false，这里改为 true
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        if (mPlayer.isPlaying())
                            mPlayer.stop();

                        if (!isRefresh && isFirst){
                            getPullAsyncDataList(str, adapter, position , listView);
                        }
                        if (isFirst && isRefresh){
                            getPullAsyncDataList(url, adapter, position , listView);
                        }
                    }

                }.execute();
            }

            @Override
            public void onLoadMore() {
                str = loadForTime(url);
                getPullAsyncDataList(str, adapter, position , listView);
                isRefresh = false;
            }
        });
    }

    private String loadForTime(String url){
        int num = 10;
        String str = url;
        num += 10;
        StringBuilder sb = new StringBuilder("");
        sb.append(0);
        sb.append(-num);

        str = str.replace("0-10", sb.toString());

        return str;
    }


    /**
     *  利用第三方开源包下载
     */
    public void getAsyncDataList(String url, final VideoAdapter adapter,
                                 final int position) {

        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (isFirst){
                    forGson(s, adapter, position);
                }
            }
        });

    }



    /**
     *  利用第三方开源包下载
     */
    public void getPullAsyncDataList(String url, final VideoAdapter adapter,
                                     final int position, final XListView listView) {

        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                forGson(s, adapter, position);
                listView.setRefreshTime(
                        new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()));
                // 下载成功之后停止刷新（减少内存消耗）
                listView.stopRefresh();
                // 下载成功后停止下载（减少内存消耗）
                listView.stopLoadMore();
            }
        });

    }

    /**
     *  解析 JSON
     */
    private void forGson(String str, VideoAdapter adapter, int position){

        Gson gson = new Gson();

        VideoTotalT videoTotalT ;
        VideoGaoXiaoT videoGaoXiaoT;
        List<VideoStrT> videoStrTs = null;

        switch (position) {
            case 0:
                videoTotalT = gson.fromJson(str, VideoTotalT.class);
                videoStrTs = videoTotalT.getV9LG4B3A0();
                break;
            case 1:
                videoGaoXiaoT = gson.fromJson(str, VideoGaoXiaoT.class);
                videoStrTs = videoGaoXiaoT.getV9LG4E6VR();
                break;
            default:
                Toast.makeText(context, "正在努力扩展中！！", Toast.LENGTH_LONG).show();
        }


        adapter.setVideoAdapterListInfo(videoStrTs);

    }
}
