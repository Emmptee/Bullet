package com.souha.bullet.home.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.data.HomeItem;
import com.souha.bullet.databinding.ItemHomeListBinding;

public class HomeListHolder extends RecyclerView.ViewHolder {

    private ItemHomeListBinding binding;
    private HomeItem homeItem;

    public HomeListHolder(ItemHomeListBinding homeListBinding,
                          OnItemClickListener<HomeItem> listener) {
        super(homeListBinding.getRoot());
        this.binding = homeListBinding;
        homeListBinding.rootCard.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                if (listener != null) {
                    listener.onItemClick(v,getAdapterPosition(),homeItem);
                }
            }
        });
    }

    public void bind(HomeItem bean) {
        homeItem = bean;
        binding.setBean(bean);
        binding.executePendingBindings();
    }
}
