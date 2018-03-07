package com.souha.bullet.news.fragment;

import android.view.View;

import com.souha.bullet.R;
import com.souha.bullet.base.BaseListFragment;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.data.Comment;
import com.souha.bullet.databinding.FragNiceCommentBinding;
import com.souha.bullet.news.activity.NewDetailActivity;
import com.souha.bullet.news.adapter.HotCommentAdapter;
import com.souha.bullet.news.viewmodel.NiceCommentViewModel;


public class NiceCommentFragment extends BaseListFragment<FragNiceCommentBinding>
        implements OnItemClickListener<Comment> {

    private NiceCommentViewModel viewModel;

    public static NiceCommentFragment newInstance() {
        return new NiceCommentFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_nice_comment;
    }

    @Override
    protected void initData() {
        viewModel = new NiceCommentViewModel();
        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        HotCommentAdapter adapter = new HotCommentAdapter(viewModel, this);
        recyclerView.setAdapter(adapter);

        viewModel.getNiceCommentLiveData().observe(this, refreshListModel -> {
            if (refreshListModel != null) {
                if (refreshListModel.isRefreshType()) {
                    adapter.setRefreshData(refreshListModel.list);

                } else {
                    adapter.setUpdateData(refreshListModel.list);

                }
            }
        });

        viewModel.initLocalNiceCommentList();

        onRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        viewModel.clear();
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position, Comment bean) {
        NewDetailActivity.launch(getActivity(),
                bean.row_id, bean.newstitle.title, "");
    }
}
