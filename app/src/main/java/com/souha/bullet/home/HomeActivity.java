package com.souha.bullet.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.souha.bullet.Account;
import com.souha.bullet.MainActivity;
import com.souha.bullet.R;
import com.souha.bullet.Utils.ResUtils;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.data.Special;
import com.souha.bullet.databinding.ActivityHomeBinding;
import com.souha.bullet.home.adapter.HomeAdapter;
import com.souha.bullet.login.LoginActivity;
import com.souha.bullet.setting.SettingActivity;
import com.souha.bullet.source.AwakerRepository;

import com.souha.bullet.base.listener.onPageSelectedListener;


import java.util.Arrays;
import java.util.List;

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
    private MaterialSheetFab materialSheetFab;
    
    private long firstTime = 0;
    
    private HomeClickListener homeClickListener = new HomeClickListener();
    private HomeAdapter mHomeAdapter;


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
        initNavigationViewMenu();

        setUpFab();
        setUpTabs();

    }

    private void setUpTabs() {
        List<String> titles = Arrays.asList(ResUtils.getString(R.string.home),ResUtils.getString(R.string.news),
                ResUtils.getString(R.string.video));

        mHomeAdapter = new HomeAdapter(getSupportFragmentManager(), titles);
        binding.viewpager.setAdapter(mHomeAdapter);
        binding.viewpager.setOffscreenPageLimit(titles.size());
        binding.tabs.setupWithViewPager(binding.viewpager);

        materialSheetFab.hideSheetThenFab();
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateFab(position);

                binding.toolbar.postDelayed(() ->{
                    Fragment fragment = mHomeAdapter.getCurrentFragment(position);
                    if (fragment instanceof onPageSelectedListener){
                        ((onPageSelectedListener) fragment).onPageSelected(position);
                    }
                },DEFAULT_DURATION);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.toolbar.post(() ->{
            if (binding.viewpager != null) {
                binding.viewpager.setCurrentItem(HomeAdapter.NEW,false);
            }
        });
    }

    private void updateFab(int position) {
        switch(position){
            case HomeAdapter.HOME:
                materialSheetFab.hideSheetThenFab();
                break;
            case HomeAdapter.NEW:
                materialSheetFab.hideSheetThenFab();
                break;
            case HomeAdapter.VIDEO:
                materialSheetFab.showFab();
                break;
        }
    }

    private void setUpFab() {
        int sheetColor = getResources().getColor(R.color.white);
        int fabColor = getResources().getColor(R.color.colorAccent);
        materialSheetFab = new MaterialSheetFab<>(binding.fab, binding.fabSheet, binding.overlay, sheetColor, fabColor);
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                super.onShowSheet();
            }

            @Override
            public void onHideSheet() {
                super.onHideSheet();
            }
        });

        binding.fabSheetItemUfo.setOnClickListener(homeClickListener);
        binding.fabSheetItemTheory.setOnClickListener(homeClickListener);
        binding.fabSheetItemSpirit.setOnClickListener(homeClickListener);
        binding.fabSheetItemFree.setOnClickListener(homeClickListener);
        binding.fabSheetItemNormal.setOnClickListener(homeClickListener);
    }

    private void initNavigationViewMenu() {
        //使ICON颜色恢复本色
        binding.navigationView.setItemIconTintList(null);
        ColorStateList csl = getResources().getColorStateList(R.color.navigation_menu_item_color);
        binding.navigationView.setItemTextColor(csl);

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.develop_desc:
                    SettingActivity.launch(this,SettingActivity.DEVELOP_DESC);
                    break;
                case R.id.cache_clear:
                    handlerCacheClear();
                    break;
                case R.id.user_back:
                    SettingActivity.launch(this, SettingActivity.USER_BACK);
                    break;
                default:
                    Toast.makeText(this, R.string.future_desc, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    private void handlerCacheClear() {
        new MaterialDialog.Builder(this)
                .title(R.string.cache_clear)
                .content(R.string.cache_clear_desc)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .theme(Theme.DARK)
                .negativeColorRes(R.color.text_color)
                .positiveColorRes(R.color.text_color)
                .onPositive((dialog,which) ->{
                    AwakerRepository.get().clearAll();
                    Toast.makeText(this,R.string.cache_clear_finish, Toast.LENGTH_SHORT).show();
                })
                .onNegative((dialog,which) ->dialog.dismiss())
                .show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                toggleDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleDrawer() {
        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private class HomeClickListener extends DebouncingOnClickListener{

        @Override
        public void doclick(View v) {
            switch(v.getId()){
                case R.id.fab_sheet_item_ufo:
                    mHomeAdapter.setCat(Special.UFO);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_theory:
                    mHomeAdapter.setCat(Special.THEORY);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_spirit:
                    mHomeAdapter.setCat(Special.SPIRIT);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_free:
                    mHomeAdapter.setCat(Special.FREE);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_normal:
                    mHomeAdapter.setCat(Special.NORMAL);
                    materialSheetFab.hideSheet();
                    break;
            }
        }
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
