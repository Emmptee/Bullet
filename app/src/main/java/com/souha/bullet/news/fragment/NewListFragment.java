package com.souha.bullet.news.fragment;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.souha.bullet.R;
import com.souha.bullet.Utils.LogUtils;
import com.souha.bullet.base.BaseListFragment;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.base.listener.onPageSelectedListener;
import com.souha.bullet.data.News;
import com.souha.bullet.data.other.RefreshListModel;
import com.souha.bullet.databinding.FragNewBinding;
import com.souha.bullet.news.activity.NewDetailActivity;
import com.souha.bullet.news.adapter.NewListAdapter;
import com.souha.bullet.news.viewmodel.NewListViewModel;

public class NewListFragment extends BaseListFragment<FragNewBinding>
        implements OnItemClickListener<News>, onPageSelectedListener {

    private static final String NEW_ID = "newId";

    private boolean isFirst;

    public static NewListFragment newInstance(int newId) {
        Bundle args = new Bundle();
        args.putInt(NEW_ID, newId);
        NewListFragment fragment = new NewListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new;
    }

    @Override
    protected void initData() {
        int newId = getArguments().getInt(NEW_ID, 0);

        NewListViewModel newListViewModel = new NewListViewModel(newId);
        setListViewModel(newListViewModel);
        binding.setViewModel(newListViewModel);

        NewListAdapter adapter = new NewListAdapter(this);
        binding.recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        newListViewModel.getNewsLiveData().observe(this, refreshListModel -> {
            if (refreshListModel != null) {
                if (RefreshListModel.REFRESH == refreshListModel.refreshType) {
                    adapter.setRefreshData(refreshListModel.list);

                } else if (RefreshListModel.UPDATE == refreshListModel.refreshType) {
                    adapter.setUpdateData(refreshListModel.list);
                }
            }
        });

        newListViewModel.initLocalNews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, News bean) {
        String url = bean.cover_url == null ? "" : bean.cover_url.ori;
        NewDetailActivity.launch(getContext(), bean.id, bean.title, url);
    }

    @Override
    public void onPageSelected(int position) {
        LogUtils.d("NewListFragment onPageSelected");
        if (!isFirst) {
            onRefresh();
            isFirst = true;
        }
    }
}
