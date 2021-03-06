package com.souha.bullet.news.adapter.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.souha.bullet.R;
import com.souha.bullet.Utils.ResUtils;
import com.souha.bullet.Utils.UiUtils;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.data.Comment;
import com.souha.bullet.data.User;
import com.souha.bullet.databinding.ItemNewDetailCommentBinding;
import com.souha.bullet.imageloader.ImageLoader;


public class NewDetailCommentHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailCommentBinding binding;
    private Comment comment;

    public NewDetailCommentHolder(ItemNewDetailCommentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        UiUtils.setTint(itemView.getContext(), R.mipmap.zan, R.color.grey_zan,
                binding.zanIv);

        binding.zanLl.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                comment.isSelect = !comment.isSelect;
                int color = comment.isSelect ? R.color.blue_zan : R.color.grey_zan;
                UiUtils.setTint(itemView.getContext(), R.mipmap.zan, color, binding.zanIv);
                int up = Integer.valueOf(comment.up);
                int newUp = comment.isSelect ? (up + 1) : up;
                binding.zanTv.setText(String.valueOf(newUp));
            }

        });
    }

    private void isSelect(boolean isSelect) {
        int color = isSelect ? R.color.blue_zan : R.color.grey_zan;
        UiUtils.setTint(itemView.getContext(), R.mipmap.zan, color, binding.zanIv);
        int up = Integer.valueOf(comment.up);
        int newUp = isSelect ? (up + 1) : up;
        binding.zanTv.setText(String.valueOf(newUp));
    }

    public void bind(Comment bean) {
        comment = bean;
        binding.contentTv.setText(bean.content);
        binding.timeTv.setText(bean.create_time);
        binding.areaTv.setText("( " + bean.area + " )");
        binding.zanTv.setText(bean.up);
        isSelect(comment.isSelect);

        User user = bean.user;
        if (user != null) {
            String userName = TextUtils.isEmpty(user.real_nickname) ?
                    ResUtils.getString(R.string.comment_guest) : user.real_nickname;
            binding.nameTv.setText(userName);

            boolean isGuest = "0".equals(bean.uid);
            binding.nameTv.setTextColor(isGuest ?
                    Color.parseColor("#FF383838") : Color.parseColor("#FFEC6A5C"));
            if (isGuest) {
                binding.iconIv.setImageResource(R.drawable.ic_gongjihui);

            } else {
                ImageLoader.get().loadCropCircle(binding.iconIv, user.avatar64);
            }
        }

        if (!TextUtils.isEmpty(bean.sina_name) && !TextUtils.isEmpty(bean.sina_avatar)) {
            binding.nameTv.setText(bean.sina_name);
            binding.nameTv.setTextColor(Color.parseColor("#FF60C5BA"));
            ImageLoader.get().loadCropCircle(binding.iconIv, bean.sina_avatar);
        }
    }
}
