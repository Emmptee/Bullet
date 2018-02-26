package com.souha.bullet.setting.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.souha.bullet.R;
import com.souha.bullet.base.BaseFragment;
import com.souha.bullet.databinding.FragDevelopDescBinding;

/**
 * Created by shidongfang on 2018/2/24.
 */

public class DevelopDescFragment extends BaseFragment<FragDevelopDescBinding>{

    public static DevelopDescFragment newInstance(){
        return new DevelopDescFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_develop_desc;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.setTitle(R.string.develop_desc);
        setToolBar(binding.toolbar);

        String versionName = AppUtils.getAppVersionName();
        int versionCode = AppUtils.getAppVersionCode();

        String versionStr = versionName +  "build(" + versionCode +")";
        binding.versionTv.setText(versionStr);
    }
}
