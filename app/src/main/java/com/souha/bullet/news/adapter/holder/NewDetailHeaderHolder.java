package com.souha.bullet.news.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.souha.bullet.data.Header;
import com.souha.bullet.databinding.ItemNewDetailHeaderBinding;


public class NewDetailHeaderHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailHeaderBinding binding;

    public NewDetailHeaderHolder(ItemNewDetailHeaderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Header bean) {
        binding.setHeader(bean);
        binding.executePendingBindings();
    }
}
