package com.souha.bullet.base.listener;

import android.view.View;

/**
 * Created by shidongfang on 2018/2/9.
 */

public abstract class DebouncingOnClickListener implements View.OnClickListener {

    private static boolean enabled = true;

    private static final Runnable ENABLE_AGAIN = () -> enabled =true;

    @Override
    public void onClick(View v) {
        if (enabled){
            enabled =false;
            v.post(ENABLE_AGAIN);
            doclick(v);
        }
    }

    public abstract void doclick(View v);
}
