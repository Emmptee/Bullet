package com.souha.bullet.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.souha.bullet.R;
import com.souha.bullet.setting.fragment.DevelopDescFragment;
import com.souha.bullet.setting.fragment.UserBackFragment;

import java.security.Key;

/**
 * Created by shidongfang on 2018/2/24.
 */

public class SettingActivity extends AppCompatActivity{
    private static final String KEY_TYPE = "type";
    public static final int DEVELOP_DESC = 1000;
    public static final int USER_BACK = 1001;

    private UserBackFragment userBackFragment;
    private DevelopDescFragment developDescFragment;

    public static void launch(Context context,int type){
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(KEY_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        int type = getIntent().getIntExtra(KEY_TYPE,DEVELOP_DESC);
        if (DEVELOP_DESC == type) {
            switchDevelopDescFragment();
        }else if (USER_BACK == type){
            switchUserBackFragment();
        }
    }

    private void switchUserBackFragment() {
        if (userBackFragment == null) {
            userBackFragment = UserBackFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl,userBackFragment,
                        UserBackFragment.class.getSimpleName())
                .commit();
    }

    private void switchDevelopDescFragment() {
        if (developDescFragment == null){
            developDescFragment = developDescFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl,developDescFragment,DevelopDescFragment.class.getSimpleName())
                .commit();
    }
}
