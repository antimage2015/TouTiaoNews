package com.crazy.toutiaonews.newsfragments;

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


public class MyViewPagerFragment extends Fragment {

    public static MyViewPagerFragment newInstance() {
        MyViewPagerFragment fragment = new MyViewPagerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_view_pager, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MainFragment.newInstance());
        fragmentList.add(YuLeFragment.newInstance());
        fragmentList.add(TiYuFragment.newInstance());
        fragmentList.add(CaiJingFragment.newInstance());
        fragmentList.add(KeJiFragment.newInstance());

        ViewPager mViewPager = (ViewPager)getView().findViewById(R.id.my_viewpager);

        SlidingTabLayout tabLayout = (SlidingTabLayout)getView().findViewById(R.id.tabs_sliding);

        mViewPager.setAdapter(new MyPagerViewAdapter(getFragmentManager(), fragmentList));

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

    public class MyPagerViewAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList;

        public MyPagerViewAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "头条";
                case 1:
                    return "娱乐";
                case 2:
                    return "体育";
                case 3:
                    return "财经";
                case 4:
                    return "科技";
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
