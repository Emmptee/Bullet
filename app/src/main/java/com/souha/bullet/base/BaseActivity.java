package com.souha.bullet.base;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.souha.bullet.R;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.base.viewmodel.BaseViewModel;


/**
 * Created by shidongfang on 2018/2/24.
 */

public abstract class BaseActivity <VB extends ViewDataBinding> extends AppCompatActivity{

    protected VB binding;
    protected BaseViewModel mViewModel;
    private RunCallBack mRunCallBack;

    protected abstract int getLayout();
    protected <T> T findViewById(View view,int id){
        return (T) view.findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayout());
    }

    @Override
    protected void onDestroy() {
        if (mViewModel != null) {
            mViewModel.isRunning.removeOnPropertyChangedCallback(mRunCallBack);
        }
        super.onDestroy();
    }

    protected void setToolBar(Toolbar toolBar){
        if (toolBar == null) {
            return;
        }
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public void addRubStatusChangeCallBack(BaseViewModel baseViewModel){
        if (baseViewModel == null) {
            return;
        }
        this.mViewModel = baseViewModel;
        if (mRunCallBack == null) {
            mRunCallBack = new RunCallBack();
        }
        this.mViewModel.isRunning.addOnPropertyChangedCallback(mRunCallBack);
    }


    private class RunCallBack extends Observable.OnPropertyChangedCallback{
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            runStateChange();
        }
    }

    private void runStateChange() {
        Log.d("BaseActivity","runStateChange");
    }
}
