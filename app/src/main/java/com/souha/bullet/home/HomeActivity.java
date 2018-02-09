package com.souha.bullet.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.souha.bullet.Account;
import com.souha.bullet.MainActivity;
import com.souha.bullet.R;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.databinding.ActivityHomeBinding;
import com.souha.bullet.login.LoginActivity;

/**
 * Created by shidongfang on 2018/2/5.
 */

public class HomeActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_HOME = 1000;
    private static final int BACK_TIME = 2000;

    private static final int DEFAULT_DURATION = 300;

    private ActivityHomeBinding binding;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView userIconIv;
    private TextView userLoginTv;
    private TextView userRegisterTv;
    private LinearLayout userLoginLl;
    private TextView userNameTv;
    private TextView userOtherDescTv;
    private TextView userExitLoginTv;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setTitle("Emmm");
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.app_name,
                R.string.app_name);
        binding.drawerLayout.setDrawerListener(drawerToggle);

        initHeaderView();
    }

    private void initHeaderView() {
        View headerView = binding.navigationView.getHeaderView(0);
        userIconIv = headerView.findViewById(R.id.icon_iv);
        userLoginTv = headerView.findViewById(R.id.login_tv);
        userRegisterTv = headerView.findViewById(R.id.register_tv);
        userLoginLl = headerView.findViewById(R.id.login_ll);
        userNameTv = headerView.findViewById(R.id.name_tv);
        userOtherDescTv = headerView.findViewById(R.id.other_tv);
        userExitLoginTv = headerView.findViewById(R.id.exit_login_tv);
        updateAccountInfo();
        initHeaderListener();
    }

    private void initHeaderListener() {
        //userlogin
        userLoginTv.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                LoginActivity.launch(HomeActivity.this,REQUEST_CODE_HOME,
                        LoginActivity.REGISTER_FLAG);
            }
        });

        //user register
        userRegisterTv.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                LoginActivity.launch(HomeActivity.this,REQUEST_CODE_HOME,
                        LoginActivity.REGISTER_FLAG);
            }
        });

        //user logout
        userExitLoginTv.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                exitLogin();
            }
        });
    }

    private void exitLogin() {
        if (Account.get().isLogin()){
            new MaterialDialog.Builder(this)
                    .title(R.string.login_out)
                    .content(R.string.login_out_desc)
                    .positiveText(R.string.confirm)
                    .negativeText(R.string.cancel)
                    .theme(Theme.DARK)
                    .negativeColorRes(R.color.text_color)
                    .positiveColorRes(R.color.text_color)
                    .onPositive((dialog,which) ->{
                        Account.get().clearUserInfo();
                        updateAccountInfo();
                        Toast.makeText(this,R.string.login_out_finish,Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.postDelayed(() ->{
                            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)){
                                binding.drawerLayout.closeDrawer(GravityCompat.START);
                            }
                        },600);
                    })
                    .onNegative((dialog,which)-> dialog.dismiss())
                    .show();
        }else {
            Toast.makeText(this,R.string.login_no,Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAccountInfo() {
        String openId = Account.get().getOpenId();
        userLoginLl.setVisibility(TextUtils.isEmpty(openId) ?View.VISIBLE:View.GONE);
        userNameTv.setVisibility(TextUtils.isEmpty(openId) ? View.GONE:View.VISIBLE);
        userExitLoginTv.setVisibility(TextUtils.isEmpty(openId)?View.GONE:View.VISIBLE);
        userOtherDescTv.setText(TextUtils.isEmpty(openId)?R.string.launch_desc:R.string.other_desc_str);
        userIconIv.setImageResource(R.drawable.ic_gongjihui2);
        String userName = Account.get().getUserName();

        userNameTv.setText(userName);
    }
}
