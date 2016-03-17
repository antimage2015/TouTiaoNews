package com.crazy.toutiaonews.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.crazy.toutiaonews.adapterofall.MyAdapter;
import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.parsejson.AdsT;
import com.crazy.toutiaonews.parsejson.CaiJingTotalT;
import com.crazy.toutiaonews.parsejson.ImgextraT;
import com.crazy.toutiaonews.parsejson.KeJiTotalT;
import com.crazy.toutiaonews.parsejson.StringT;
import com.crazy.toutiaonews.parsejson.TiYuTotalT;
import com.crazy.toutiaonews.parsejson.TotalT;
import com.crazy.toutiaonews.parsejson.YuLeTotalT;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.warmtel.android.xlistview.XListView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 *  Created by scxh on 2016/1/12.
 */
public class Utils {

    private Context context;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private SliderLayout sliderLayout;
    private List<AdsT> adsList;
    private boolean isFirst = true;
    private boolean isRefresh = true;
    private String str;


    public Utils(Context context){
        this.context = context;
    }

    private void initListHeader(XListView listView){
        View sliderHeaderView = LayoutInflater.from(context).inflate(R.layout.slider_content_layout, null);
        sliderLayout = (SliderLayout)sliderHeaderView.findViewById(R.id.slider_content_image);
        listView.addHeaderView(sliderHeaderView);

        HashMap<String,String> sliderList = getData();
        for(String key : sliderList.keySet()){
            String url = sliderList.get(key);
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView.description(key);
            textSliderView.image(url);
            textSliderView.setScaleType(TextSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);

        }
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
    }

    /* ListView 的头部加载数据来源 */
    private HashMap<String,String> getData(){

        HashMap<String,String> http_url_maps = new HashMap<String, String>();
        for (AdsT xlist : adsList) {
            http_url_maps.put(xlist.getTitle(), xlist.getImgsrc());
        }
        return http_url_maps;
    }


    public void clickForXListView(final String url, final MyAdapter adapter,
                                  final XListView listView, final int position) {
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

                        if (!isRefresh && isFirst){
                            getPullAsyncDataList(str, adapter, listView, position);
                        }
                        if (isFirst && isRefresh){
                            getPullAsyncDataList(url, adapter, listView, position);
                        }
                    }

                }.execute();
            }

            @Override
            public void onLoadMore() {
                str = loadForTime(url);
                getPullAsyncDataList(str, adapter, listView, position);
                isRefresh = false;
            }
        });
    }

    private String loadForTime(String url){
        int num = 20;
        String str = url;
        num += 20;
        StringBuilder sb = new StringBuilder("");
        sb.append(0);
        sb.append(-num);

        str = str.replace("0-20", sb.toString());

        return str;
    }


    /**
     *  利用第三方开源包下载(不含刷新)
     */
    public void getAsyncDataList(String url, final MyAdapter adapter,
                                 final XListView listView, final int position) {

        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if(isFirst) {
                    isFirst = false;
                    // 写入缓存
                    if (s != null)
                        JsonCache.getInstance(context).setJsonCache("click", s);

                    forGson(s, adapter, listView, position);
                }
            }
        });

    }

    /**
     *  利用第三方开源包下载(只为刷新)
     */
    public void getPullAsyncDataList(String url, final MyAdapter adapter,
                                 final XListView listView, final int position) {

        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                forGson(s, adapter, listView,position);

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
    public void forGson(String str, MyAdapter adapter, XListView listView, int position){

        Gson gson = new Gson();
        TotalT totalT ;
        YuLeTotalT yuLeTotalT;
        TiYuTotalT tiYuTotalT;
        CaiJingTotalT caiJingTotalT;
        KeJiTotalT keJiTotalT;

        // 其他属性
        List<StringT> mList = null;

        switch (position){
            case 0:
                totalT = gson.fromJson(str, TotalT.class);
                mList = totalT.getT1348647909107();
                break;
            case 1:
                yuLeTotalT = gson.fromJson(str, YuLeTotalT.class);
                mList = yuLeTotalT.getT1348648517839();
                break;
            case 2:
                tiYuTotalT = gson.fromJson(str, TiYuTotalT.class);
                mList = tiYuTotalT.getT1348649079062();
                break;
            case 3:
                caiJingTotalT = gson.fromJson(str, CaiJingTotalT.class);
                mList = caiJingTotalT.getT1348648756099();
                break;
            case 4:
                keJiTotalT = gson.fromJson(str, KeJiTotalT.class);
                mList = keJiTotalT.getT1348649580692();
                break;
            default:
                Toast.makeText(context, " 请在 Utils 工具类中的 forGson() 方法中添加相应的类及数据",
                        Toast.LENGTH_LONG).show();
        }

        // ImgextraT 的属性
 //       List<ImgextraT> imgList = mList.get(0).getImgextra();

        // AdsT 的属性 (5条)
        adsList = mList.get(0).getAds();
        adapter.setListInfo(mList, listView);


        if (!isFirst) {
            initListHeader(listView);
            isFirst = true;
        }
    }

}
