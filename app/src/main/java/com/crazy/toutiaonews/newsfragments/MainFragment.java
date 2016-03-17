package com.crazy.toutiaonews.newsfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.crazy.toutiaonews.adapterofall.MyAdapter;
import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.utils.JsonCache;
import com.crazy.toutiaonews.utils.NetState;
import com.crazy.toutiaonews.utils.Utils;
import com.scxh.slider.library.SliderLayout;
import com.warmtel.android.xlistview.XListView;


public class MainFragment extends Fragment {

    private String url = "http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
    private SliderLayout sliderLayout;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView imageView = (ImageView)getView().findViewById(R.id.fragment_main_img);

        XListView listView = (XListView)getView().findViewById(R.id.main_list_view);
        MyAdapter adapter = new MyAdapter(getActivity());

        Utils utils = new Utils(getActivity());


        // 读取缓存
        String listContentStr = JsonCache.getInstance(getActivity()).getJsonCache("click");
        // 网络连接状态
        int type = NetState.getAPNType(getActivity());
        if (listContentStr != null && type == -1){
            Log.e("tag", " 网络连接状态 -1：没有网络  1：WIFI网络 2：wap网络  3：net网络 " + type);
            utils.forGson(listContentStr, adapter, listView, 0);
        }

        listView.setAdapter(adapter);

        utils.getAsyncDataList(url, adapter, listView, 0);

        listView.setEmptyView(imageView);

        utils.clickForXListView(url, adapter, listView, 0);
    }


    @Override
    public void onPause() {
        super.onPause();
        if(sliderLayout !=null) {
            sliderLayout.stopAutoCycle();
            sliderLayout = null;
        }
    }

//    private void initListHeader(){
//        View sliderHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.slider_content_layout, null);
//        sliderLayout = (SliderLayout)sliderHeaderView.findViewById(R.id.slider_content_image);
//        listView.addHeaderView(sliderHeaderView);
//
//        HashMap<String,String> sliderList = getData();
//        for(String key : sliderList.keySet()){
//            String url = sliderList.get(key);
//            TextSliderView textSliderView = new TextSliderView(getActivity());
//            textSliderView.description(key);
//            textSliderView.image(url);
//            textSliderView.setScaleType(TextSliderView.ScaleType.CenterCrop);
//            sliderLayout.addSlider(textSliderView);
//
//        }
//        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//    }
//
//    /* ListView 的头部加载数据来源 */
//    private HashMap<String,String> getData(){
//
//        HashMap<String,String> http_url_maps = new HashMap<String, String>();
//        for (AdsT xlist : adsList) {
//            http_url_maps.put(xlist.getTitle(), xlist.getImgsrc());
//        }
//        return http_url_maps;
//    }
//
//    /**
//     *  利用第三方开源包下载
//     */
//    public void getAsyncDataList() {
//
//        asyncHttpClient.get(url, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccess(int i, Header[] headers, String s) {
//                forGson(s);
//            }
//        });
//
//    }
//
//    /**
//     *  解析 JSON
//     */
//    private void forGson(String str){
//
//        Gson gson = new Gson();
//        TotalT totalT = gson.fromJson(str, TotalT.class);
//        // 其他属性
//        List<StringT> mList = totalT.getT1348647909107();
//
//        // ImgextraT 的属性
//        List<ImgextraT> imgList = mList.get(0).getImgextra();
//
//        // AdsT 的属性 (5条)
//        adsList = mList.get(0).getAds();
//        adapter.setListInfo(mList);
//
//        initListHeader();
//
//    }

}
