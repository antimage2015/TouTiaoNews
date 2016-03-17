package com.crazy.toutiaonews.picturefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.crazy.toutiaonews.adapterofall.PictureAdapter;
import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.utils.UtilsOfPicture;
import com.warmtel.android.xlistview.XListView;


public class PictureMainFragment extends Fragment {

    // 图片新闻的 url
    private String url = "http://api.sina.cn/sinago/list.json?channel=hdpic_toutiao&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=&imei=867064013906290&uid=802909da86d9f5fc&p=";

    public static PictureMainFragment newInstance() {
        PictureMainFragment fragment = new PictureMainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture_main_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ProgressBar progressBar = (ProgressBar)getView().findViewById(R.id.pic_progressbar);
        XListView listView = (XListView)getView().findViewById(R.id.pic_frag_activity_xlistview);
        PictureAdapter adapter = new PictureAdapter(getActivity());
        listView.setAdapter(adapter);

        UtilsOfPicture uop = new UtilsOfPicture(getActivity());
        uop.getAsyncDataList(url, adapter, listView);
        listView.setEmptyView(progressBar);
        uop.clickPictureForXListView(url, adapter, listView);
    }
}
