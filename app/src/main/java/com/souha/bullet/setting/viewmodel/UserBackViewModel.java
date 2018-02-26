package com.souha.bullet.setting.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.souha.bullet.Account;
import com.souha.bullet.base.viewmodel.BaseViewModel;
import com.souha.bullet.data.UserInfo;
import com.souha.bullet.email.SendMailUtil;

public class UserBackViewModel extends BaseViewModel {

    public ObservableField<String> contentStr = new ObservableField<>();
    public ObservableBoolean isSend = new ObservableBoolean(false);

    public void sendEmail() {
        String content = contentStr.get();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        String userName = "guest";
        UserInfo userInfo = Account.get().getUserInfo();
        if (userInfo != null && userInfo.data_1 != null) {
            userName = userInfo.data_1.nickname;
        }

        SendMailUtil.send("userName: " + userName + ":   " + content);
        isSend.set(true);
    }
}
