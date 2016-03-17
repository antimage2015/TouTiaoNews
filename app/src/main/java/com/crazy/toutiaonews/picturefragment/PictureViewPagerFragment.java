package com.crazy.toutiaonews.picturefragment;

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


public class PictureViewPagerFragment extends Fragment {

    public static PictureViewPagerFragment newInstance() {
        PictureViewPagerFragment fragment = new PictureViewPagerFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_view_pager_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PictureMainFragment.newInstance());
        fragmentList.add(QuTuPictureFragment.newInstance());
        fragmentList.add(MeituPictureFragment.newInstance());
        fragmentList.add(GushiPictureFragment.newInstance());

        ViewPager mViewPager = (ViewPager)getView().findViewById(R.id.picture_my_viewpager);

        SlidingTabLayout tabLayout =
                (SlidingTabLayout)getView().findViewById(R.id.picture_tabs_sliding);

        mViewPager.setAdapter(new PicturePagerViewAdapter(getFragmentManager(), fragmentList));

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

    public class PicturePagerViewAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList;

        public PicturePagerViewAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "精选";
                case 1:
                    return "趣图";
                case 2:
                    return "美图";
                case 3:
                    return "故事";
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
