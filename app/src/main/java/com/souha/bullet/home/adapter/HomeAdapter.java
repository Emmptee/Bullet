package com.souha.bullet.home.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.souha.bullet.home.fragment.HomeListFragment;
import com.souha.bullet.news.fragment.NewListFragment;
import com.souha.bullet.video.videoListFragment;

import java.util.List;

/**
 * Created by shidongfang on 2018/2/28.
 */

public class HomeAdapter extends FragmentPagerAdapter {

    public static final int HOME = 0;
    public static final int NEW = 1;
    public static final int VIDEO = 2;

    private List<String> mTitles;
    private HomeListFragment mHomeListFragment;
    private NewListFragment mNewListFragment;
    private videoListFragment mVideoListFragment;

    public HomeAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.mTitles = titles;
    }

    public void setCat(int cat){
        if (mVideoListFragment != null) {
            
        }
    }

    public Fragment getCurrentFragment(int position){
        Fragment fragment = null;
        switch(position){
            case HOME:
                fragment = mHomeListFragment;
                break;
            case NEW:
                fragment = mNewListFragment;
                break;
            case VIDEO:
                fragment = mVideoListFragment;
                break;
        }
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case HOME:
                mHomeListFragment = HomeListFragment.newInstance();
                fragment= mHomeListFragment;
                break;
            case NEW:
                //TODO
                fragment= mNewListFragment;
                break;
            case VIDEO:
                fragment = mVideoListFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 :mTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
