package com.souha.bullet.home.adapter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.souha.bullet.MyApplication;
import com.souha.bullet.R;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.data.BannerItem;

import java.util.List;

/**
 * Created by shidongfang on 2018/3/1.
 */

public class HomeBannerAdapter extends PagerAdapter {

    private SparseArray<View> mViews = new SparseArray<>();
    private List<BannerItem> mBannerItemList;

    public void setData(List<BannerItem> bannerItemList) {
        this.mBannerItemList = bannerItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_home_banner_item,
                    container, false);
            mViews.put(position, view);
        }
        initViewToData(view, position);
        container.addView(view);
        return view;
    }

    private void initViewToData(View view, int position) {
        ImageView imageView = view.findViewById(R.id.banner_iv);
        BannerItem bannerItem = mBannerItemList.get(position);
        imageView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                //TODO NewDetailActivity
                Toast.makeText(MyApplication.get(), "NewDetailActivity 待定", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        return mBannerItemList == null ? 0 : mBannerItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
