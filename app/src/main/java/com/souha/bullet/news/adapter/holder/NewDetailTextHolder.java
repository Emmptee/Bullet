package com.souha.bullet.news.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.souha.bullet.data.NewEle;
import com.souha.bullet.databinding.ItemNewDetailTextBinding;


public class NewDetailTextHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailTextBinding binding;

    public NewDetailTextHolder(ItemNewDetailTextBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(NewEle bean) {
        binding.setNewEle(bean);
        binding.executePendingBindings();
    }
}
