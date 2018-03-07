package com.souha.bullet.news.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.data.NewEle;
import com.souha.bullet.databinding.ItemNewDetailCommentMoreBinding;

public class NewDetailCommentMoreHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailCommentMoreBinding binding;
    private OnItemClickListener<NewEle> listener;

    public NewDetailCommentMoreHolder(ItemNewDetailCommentMoreBinding binding,
                                      OnItemClickListener<NewEle> listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
        binding.moreFl.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, getAdapterPosition(), null);
                }
            }

        });
    }
}
