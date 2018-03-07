package com.souha.bullet.video.fragment;

import android.os.Bundle;
import android.view.View;

import com.souha.bullet.R;
import com.souha.bullet.base.BaseListFragment;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.data.News;
import com.souha.bullet.databinding.FragSpecialListBinding;
import com.souha.bullet.video.adapter.SpecialListAdapter;
import com.souha.bullet.video.viewmodel.SpecialListViewModel;


public class SpecialListFragment extends BaseListFragment<FragSpecialListBinding> implements OnItemClickListener<News> {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String URL = "url";

    private SpecialListViewModel viewModel = new SpecialListViewModel();
    private String url;

    public static SpecialListFragment newInstance(String id, String title, String url) {
        Bundle args = new Bundle();
        SpecialListFragment fragment = new SpecialListFragment();
        args.putString(ID, id);
        args.putString(TITLE, title);
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_special_list;
    }

    @Override
    protected void initData() {
        String id = getArguments().getString(ID);
        String title = getArguments().getString(TITLE);
        url = getArguments().getString(URL);

        viewModel.setParams(id, title, url);

        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        setToolBar(binding.toolbar);

        SpecialListAdapter adapter = new SpecialListAdapter(viewModel, this);
        recyclerView.setAdapter(adapter);

        onRefresh();
    }

    @Override
    public void onDestroy() {
        viewModel.clear();
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position, News bean) {
//        NewDetailActivity.launch(getActivity(), bean.id, bean.title, url);
    }
}
