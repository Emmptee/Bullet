package com.souha.bullet.video.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.souha.bullet.R;
import com.souha.bullet.base.BaseActivity;
import com.souha.bullet.databinding.ActivitySpecialListBinding;
import com.souha.bullet.video.fragment.SpecialListFragment;

public class SpecialListActivity extends BaseActivity<ActivitySpecialListBinding> {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String URL = "url";

    private SpecialListFragment specialListFragment;

    public static void launch(Context context, String id, String title, String url) {
        Intent intent = new Intent(context, SpecialListActivity.class);
        intent.putExtra(ID, id);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_special_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String id = getIntent().getStringExtra(ID);
        String title = getIntent().getStringExtra(TITLE);
        String url = getIntent().getStringExtra(URL);

        if (TextUtils.isEmpty(id)) {
            finish();
        }

        if (specialListFragment == null) {
            specialListFragment = SpecialListFragment.newInstance(id, title, url);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fl, specialListFragment,
                            SpecialListFragment.class.getSimpleName())
                    .commit();
        }
    }
}
