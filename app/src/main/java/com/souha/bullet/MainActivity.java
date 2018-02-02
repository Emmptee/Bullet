package com.souha.bullet;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.SPUtils;
import com.souha.bullet.Utils.ConstantUtils;
import com.souha.bullet.imageloader.ImageLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SHOW_LAUNCH = "showlaunch";
    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        boolean isShow = SPUtils.getInstance().getBoolean(SHOW_LAUNCH);
        long delayMillis = !isShow && ConstantUtils.isReleaseBuild() ? 2500 : 0;
        if (delayMillis >0){
            SPUtils.getInstance().put(SHOW_LAUNCH,true);

        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }
}
