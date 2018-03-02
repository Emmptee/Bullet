package com.souha.bullet.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.souha.bullet.data.HomeItem;

import com.souha.bullet.R;
import com.souha.bullet.base.BaseActivity;
import com.souha.bullet.databinding.ActivityNewListBinding;

/**
 * Created by shidongfang on 2018/3/2.
 */

public class HomeTypeListActivity extends BaseActivity<ActivityNewListBinding>{

    private static final String NEW_ID = "newId";
    private static final String NEW_TITLE= "newTitle";


    public static void launch(Context context,int newId,String title){
        Intent intent = new Intent(context, HomeTypeListActivity.class);
        intent.putExtra(NEW_ID,newId);
        intent.putExtra(NEW_TITLE,title);
        context.startActivity(intent);
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_new_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int newId = getIntent().getIntExtra(NEW_ID, 0);
        String newTitle = getIntent().getStringExtra(NEW_TITLE);
        binding.toolbar.setTitle(newTitle);
        setToolBar(binding.toolbar);
        switchPage(newId);
    }

    private void switchPage(int newId) {
        switch(newId){
            case HomeItem.NICE_COMMENT:
                //TODO 跳转Fragment
                break;
        }
    }

    public void showFragment(Fragment fragment,String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl,fragment,tag)
                .commit();
    }
}
