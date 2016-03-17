package com.crazy.toutiaonews.videofragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;


public class VideoViewPagerFragment extends Fragment {


    public static VideoViewPagerFragment newInstance() {
        VideoViewPagerFragment fragment = new VideoViewPagerFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_view_pager_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ReDianFragment.newInstance());
        fragmentList.add(GaoXiaoVideoFragment.newInstance());

        ViewPager mViewPager = (ViewPager)getView().findViewById(R.id.video_my_viewpager);

        SlidingTabLayout tabLayout =
                (SlidingTabLayout)getView().findViewById(R.id.video_tabs_sliding);

        mViewPager.setAdapter(new VideoPagerViewAdapter(getFragmentManager(), fragmentList));

        tabLayout.setViewPager(mViewPager);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.RED;
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });

    }


    public class VideoPagerViewAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList;

        public VideoPagerViewAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "热点";
                case 1:
                    return "搞笑";
                case 2:
                    return "精品";
                case 3:
                    return "娱乐";
                default:
                    return "";
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
