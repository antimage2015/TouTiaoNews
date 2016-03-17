package com.crazy.toutiaonews.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.crazy.toutiaonews.adapterofall.PictureAdapter;
import com.crazy.toutiaonews.picturenewsparsejson.PictureDataT;
import com.crazy.toutiaonews.picturenewsparsejson.PictureListT;
import com.crazy.toutiaonews.picturenewsparsejson.PictureTotalT;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.warmtel.android.xlistview.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 *  Created by scxh on 2016/1/14.
 */
public class UtilsOfPicture {

    private Context context;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public UtilsOfPicture(Context context) {
        this.context = context;
    }


    public void clickPictureForXListView(final String url, final PictureAdapter adapter,
                                  final XListView listView) {
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

                        getAsyncDataList(url, adapter, listView);
                    }

                }.execute();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(context, "Sorry ! 没有更多的图片了！", Toast.LENGTH_SHORT).show();
                getAsyncDataList(url, adapter, listView);
            }
        });
    }

    public void getAsyncDataList(String url, final PictureAdapter adapter
                            , final XListView listView) {

        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                forGson(s, adapter);
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
    private void forGson(String str, PictureAdapter adapter){

        MyParcelable mp = new MyParcelable();
        mp.setStr(str);

        Gson gson = new Gson();

        PictureTotalT pictureTotalT = gson.fromJson(str, PictureTotalT.class);

        PictureDataT pictureDataT = pictureTotalT.getData();
        // 该集合包括（总数:下标从 0 到 19） title pic 和 跟帖的数量
        List<PictureListT> pictureListTs = pictureDataT.getList();

        adapter.setPicAdapterListInfo(pictureListTs);


//        for (PictureListT pcL : pictureListTs) {
//            String title = pcL.getTitle();
//            String pic = pcL.getPic();
//
//            Log.e("================", "=== title ===" + title + ", == pic ==" + pic);
//        }

        // 跟帖的数量(下标为0)
//        PictureGenTieT pictureGenTieT = pictureListTs.get(0).getComment_count_info();
//        String genTieSum = pictureGenTieT.getQreply();
//        Log.e("================", "=== genTieSum ===" + genTieSum);

        // pics (第一个张图，也就是下标为0)下共有几张 pic (二级菜单可用)
//        PicturePicsT picturePicsT = pictureListTs.get(0).getPics();
//        List<PicturePicsListT> picturePicsListTs = picturePicsT.getList();

//        Log.e("================","=== picturePicsListTs ==="+picturePicsListTs.size());

//        for (PicturePicsListT picsForEvery : picturePicsListTs) {
//            String alts =picsForEvery.getAlt();
//            String picOfAlts = picsForEvery.getPic();
//            Log.e("================","=== alts ==="+alts +", == picOfAlts ==" +picOfAlts);
//        }
    }
}
