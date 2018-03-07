package com.souha.bullet.news.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.souha.bullet.R;
import com.souha.bullet.base.BaseListFragment;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.data.News;
import com.souha.bullet.databinding.FragNewHotReadBinding;
import com.souha.bullet.news.activity.NewDetailActivity;
import com.souha.bullet.news.adapter.NewListAdapter;
import com.souha.bullet.news.viewmodel.HotReadViewModel;
import com.souha.bullet.source.AwakerRepository;


public class HotReadNewsFragment extends BaseListFragment<FragNewHotReadBinding>
        implements OnItemClickListener<News> {

    private HotReadViewModel viewModel;
    private NewListAdapter adapter;

    public static HotReadNewsFragment newInstance() {
        return new HotReadNewsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new_hot_read;
    }

    @Override
    protected void initData() {
        viewModel = new HotReadViewModel();
        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        adapter = new NewListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        onRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroyView() {
        viewModel.clear();
        AwakerRepository.get().close();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, News bean) {
        String url = bean.cover_url == null ? "" : bean.cover_url.ori;
        NewDetailActivity.launch(getContext(), bean.id, bean.title, url);
    }
}
