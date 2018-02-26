package com.souha.bullet.setting.fragment;

import android.app.Application;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.souha.bullet.MyApplication;
import com.souha.bullet.R;
import com.souha.bullet.base.BaseFragment;
import com.souha.bullet.base.listener.DebouncingOnClickListener;
import com.souha.bullet.databinding.FragUserBackBinding;
import com.souha.bullet.setting.viewmodel.UserBackViewModel;


/**
 * Created by shidongfang on 2018/2/24.
 */

public class UserBackFragment extends BaseFragment<FragUserBackBinding>{

    private UserBackViewModel userBackViewModel;
    private SendCallBack sendCallBack = new SendCallBack();

    public static UserBackFragment newInstance(){
        return new UserBackFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_user_back;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.setTitle(R.string.user_back);
        setToolBar(binding.toolbar);

        userBackViewModel = new UserBackViewModel();
        binding.setViewModel(userBackViewModel);
        addRunStatusChangeCallBack(userBackViewModel);

        userBackViewModel.isSend.addOnPropertyChangedCallback(sendCallBack);
        initListener();
    }

    private void initListener() {
        binding.contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = s.toString();
                binding.sendBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.sendBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doclick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.send_email)
                        .content(R.string.send_email_content)
                        .positiveText(R.string.confirm)
                        .negativeText(R.string.cancel)
                        .theme(Theme.LIGHT)
                        .contentColorRes(R.color.colorPrimary)
                        .negativeColorRes(R.color.text_color)
                        .positiveColorRes(R.color.text_color)
                        .onPositive((dialog, which) -> userBackViewModel.sendEmail())
                        .onNegative((dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

    private class SendCallBack extends Observable.OnPropertyChangedCallback{

        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (userBackViewModel.isSend.get()){
                Toast.makeText(MyApplication.get(), R.string.send_email_finish,Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}
