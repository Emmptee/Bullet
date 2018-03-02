package com.souha.bullet.home.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.souha.bullet.data.Banner;
import com.souha.bullet.databinding.ItemHomeBannerBinding;
import com.souha.bullet.home.adapter.HomeBannerAdapter;

/**
 * Created by shidongfang on 2018/3/1.
 */

public class HomeBannerHolder extends RecyclerView.ViewHolder {

    private ItemHomeBannerBinding binding;
    private HomeBannerAdapter homeBannerAdapter;

    public HomeBannerHolder(ItemHomeBannerBinding homeBannerBinding) {
        super(homeBannerBinding.getRoot());
        this.binding = homeBannerBinding;
        homeBannerAdapter = new HomeBannerAdapter();
        binding.viewPager.setAdapter(homeBannerAdapter);
    }

    public void bind(Banner bean ){
        homeBannerAdapter.setData(bean.list);
    }
}
