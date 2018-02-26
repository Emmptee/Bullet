package com.souha.bullet.base;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.souha.bullet.R;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.base.viewmodel.BaseViewModel;

/**
 * Created by shidongfang on 2018/2/26.
 */

public abstract class BaseFragment <VB extends ViewDataBinding> extends Fragment{
    protected ProgressDialog mProgressDialog;
    protected VB binding;
    protected BaseViewModel mViewModel;
    private RunCallBack mRunCallBack;

    protected <T> T findViewById(View view,int id){
        return (T) view.findViewById(id);
    }

    protected abstract int getLayout();
    protected void onCreateBindView(){

    }

    public void showProgressDialog(int message){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this.getActivity());

        }
        mProgressDialog.setMessage(getString(message));
        mProgressDialog.show();
    }

    public void dismissProgressDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        onCreateBindView();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        if (mViewModel != null) {
            mViewModel.isRunning.removeOnPropertyChangedCallback(mRunCallBack);
        }
        super.onDestroy();
    }

    protected void setToolBar(Toolbar toolBar){
        if (toolBar == null) {
            return;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                getActivity().onBackPressed();
            }
        });

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addRunStatusChangeCallBack(BaseViewModel baseViewModel){
        if (baseViewModel == null) {
            return;
        }
        this.mViewModel = baseViewModel;
        if (mRunCallBack == null) {
            mRunCallBack= new RunCallBack();
        }
        this.mViewModel.isRunning.addOnPropertyChangedCallback(mRunCallBack);
    }

    protected void runStatusChange(boolean isRunning) {
        if (isRunning) {
            showProgressDialog(R.string.wait);
        } else {
            dismissProgressDialog();
        }
    }

    private class RunCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            runStatusChange(mViewModel.isRunning.get());
        }
    }
}
