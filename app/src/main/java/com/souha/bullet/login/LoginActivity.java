package com.souha.bullet.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.souha.bullet.R;
import com.souha.bullet.data.UserInfo;
import com.souha.bullet.login.listener.LoginListener;

/**
 * Created by shidongfang on 2018/2/9.
 */

public class LoginActivity extends AppCompatActivity implements LoginListener{
    private static final String TYPE_FLAG = "typeFlag";
    public static final int RESULT_CODE_SUC = 10001;

    public static final int LOGIN_FLAG = 3000;
    public static final int REGISTER_FLAG = 3001;

    public static void launch(Activity activity,int requestCode,int typeFlag){
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(TYPE_FLAG,typeFlag);
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        int typeFlag = getIntent().getIntExtra(TYPE_FLAG,REGISTER_FLAG);
        if (typeFlag == LOGIN_FLAG){
            swichLoginFragment();
        }else if (typeFlag == REGISTER_FLAG){
            switchRegisterFragment();
        }else {
            switchRegisterFragment();
        }
    }

    private void swichLoginFragment() {
        if (TYPE_FLAG == null) {
            //TODO LoginFragment
        }
    }

    private void switchRegisterFragment() {

    }

    @Override
    public void onLoginSuc(UserInfo userInfo) {

    }
}
