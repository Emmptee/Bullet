package com.souha.bullet.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.souha.bullet.R;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.data.BannerItem;
import com.souha.bullet.imageloader.ImageLoader;

import java.util.List;

/**
 * Created by shidongfang on 2018/3/1.
 */

public class HomeBannerAdapter extends PagerAdapter {

    private SparseArray<View> mViews = new SparseArray<>();
    private List<BannerItem> bannerItemList;

    public void setData(List<BannerItem> bannerItemList) {
        this.bannerItemList = bannerItemList;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.item_home_banner_item, container,
                            false);
            mViews.put(position, view);
        }
        initViewToData(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void initViewToData(View view, int position) {
        ImageView imageView =
                (ImageView) view.findViewById(R.id.banner_iv);
        BannerItem bannerItem = bannerItemList.get(position);
        ImageLoader.get().load(imageView, bannerItem.img_url);

        imageView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
//                NewDetailActivity.launch(v.getContext(), bannerItem.newsid,
//                        bannerItem.title, bannerItem.img_url);
            }
        });
    }

    @Override
    public int getCount() {
        return bannerItemList == null ? 0 : bannerItemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
