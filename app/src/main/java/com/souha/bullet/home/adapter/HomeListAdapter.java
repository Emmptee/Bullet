package com.souha.bullet.home.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.souha.bullet.R;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.data.Banner;
import com.souha.bullet.data.BannerItem;
import com.souha.bullet.data.HomeItem;
import com.souha.bullet.databinding.ItemHomeBannerBinding;
import com.souha.bullet.databinding.ItemHomeListBinding;
import com.souha.bullet.home.adapter.holder.HomeBannerHolder;
import com.souha.bullet.home.adapter.holder.HomeListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shidongfang on 2018/3/1.
 */

public class HomeListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BANNER = 1000;
    private static final int TYPE_ITEM = 1001;

    private static final int SPAN_COUNT = 3;

    private List<Object> dataList = new ArrayList<>();
    private OnItemClickListener<HomeItem> listener;

    public HomeListAdapter(OnItemClickListener<HomeItem> listener) {
        this.listener = listener;
    }

    public void setData(List<BannerItem> list) {
        if (list == null) {
            return;
        }
        Banner banner = new Banner(list);
        dataList.clear();

        dataList.add(banner);
        dataList.addAll(HomeItem.getList());
        notifyDataSetChanged();
    }

    public void setData() {
        dataList.clear();

        dataList.addAll(HomeItem.getList());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = TYPE_ITEM;
        Object object = dataList.get(position);
        if (object instanceof Banner) {
            viewType = TYPE_BANNER;
        }
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_BANNER) {
            ItemHomeBannerBinding homeBannerBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_home_banner, parent, false);
            viewHolder = new HomeBannerHolder(homeBannerBinding);

        } else if (viewType == TYPE_ITEM) {
            ItemHomeListBinding homeListBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_home_list, parent, false);
            viewHolder = new HomeListHolder(homeListBinding, listener);

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER) {
            ((HomeBannerHolder) holder).bind((Banner) dataList.get(position));
        } else if (viewType == TYPE_ITEM) {
            ((HomeListHolder) holder).bind((HomeItem) dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public int getSpanSize(int position) {
        int spanSize = 1;
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER) {
            spanSize = SPAN_COUNT;
        }
        return spanSize;
    }
}
