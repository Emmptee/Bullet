package com.souha.bullet.news.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.souha.bullet.R;
import com.souha.bullet.base.EmptyHolder;
import com.souha.bullet.base.IDiffCallBack;
import com.souha.bullet.base.viewmodel.BaseListViewModel;
import com.souha.bullet.data.Comment;
import com.souha.bullet.databinding.ItemLoadBinding;
import com.souha.bullet.databinding.ItemNewDetailCommentBinding;
import com.souha.bullet.news.adapter.holder.NewDetailCommentHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CommentListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MORE = 1000;
    private static final int TYPE_ITEM = 1001;

    private static final String LOAD_MORE = "loadMore";

    private List<Object> commentList = new ArrayList<>();
    private List<Object> oldCommentList = new ArrayList<>();

    private BaseListViewModel viewModel;
    private CommentDiffCallBack diffCallBack = new CommentDiffCallBack();

    public CommentListAdapter(BaseListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setRefreshData(List<Comment> list) {
        if (list == null) {
            return;
        }
        commentList.clear();
        commentList.addAll(list);
        commentList.add(LOAD_MORE);
        notifyDataSetChanged();
    }

    public void setUpdateData(List<Comment> list) {
        if (list == null) {
            return;
        }
        oldCommentList.clear();
        oldCommentList.addAll(commentList);

        commentList.clear();
        commentList.addAll(list);
        commentList.add(LOAD_MORE);

        diffCallBack.setData(oldCommentList, commentList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = commentList.get(position);
        if (object instanceof String) {
            return TYPE_MORE;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemNewDetailCommentBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment, parent, false);
            return new NewDetailCommentHolder(binding);

        } else if (viewType == TYPE_MORE) {
            ItemLoadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_load, parent, false);
            binding.setViewModel(viewModel);
            return new EmptyHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_ITEM) {
            ((NewDetailCommentHolder) holder).bind((Comment) commentList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    private static class CommentDiffCallBack extends IDiffCallBack<Object> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newObj = newData.get(newItemPosition);

            if (oldObj instanceof Comment && newObj instanceof Comment) {
                Comment oldComment = (Comment) oldObj;
                Comment newComment = (Comment) newObj;
                return Objects.equals(oldComment.id, newComment.id) &&
                        Objects.equals(oldComment.content, newComment.content);

            } else if (oldObj instanceof String && newObj instanceof String) {
                String oldStr = (String) oldObj;
                String newStr = (String) newObj;
                return Objects.equals(oldStr, newStr);
            }
            return false;
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newObj = newData.get(newItemPosition);

            if (oldObj instanceof Comment && newObj instanceof Comment) {
                Comment oldComment = (Comment) oldObj;
                Comment newComment = (Comment) newObj;
                return Objects.equals(oldComment.id, newComment.id) &&
                        Objects.equals(oldComment.content, newComment.content);

            } else if (oldObj instanceof String && newObj instanceof String) {
                String oldStr = (String) oldObj;
                String newStr = (String) newObj;
                return Objects.equals(oldStr, newStr);
            }
            return false;
        }
    }
}
