package com.souha.bullet.helper;

import android.content.Context;
import com.souha.bullet.Account;
import com.souha.bullet.MyApplication;
import com.souha.bullet.Utils.LogUtils;
import com.souha.bullet.data.UserInfo;
import com.souha.bullet.data.realm.UserInfoRealm;
import com.souha.bullet.network.EmptyConsumer;
import com.souha.bullet.network.ErrorConsumer;
import com.souha.bullet.source.AwakerRepository;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;


public final class AccountHelper {

    private static final String TAG = AccountHelper.class.getSimpleName();


    public static final String OPEN_ID = "openId";
    public static final String USER_NAME = "userName";

    private AccountHelper() {
    }

    public static String getOpenId() {
        return MyApplication.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .getString(OPEN_ID, "");
    }

    public static String getUserName() {
        return MyApplication.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .getString(USER_NAME, "");
    }

    public static void initUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put(UserInfoRealm.ID, UserInfoRealm.ID_VALUE);
        AwakerRepository.get().getLocalRealm(UserInfoRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "doOnError: " + throwable.toString()))
                .doOnNext(realmResults -> {
                    LogUtils.d("initUserInfo: " + realmResults.size());
                    Account.get().setLocalUserInfo(realmResults);

                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public static void setUserInfoToLocal(UserInfo userInfo) {
        UserInfoRealm userInfoRealm = UserInfoRealm.getUserInfoRealm(userInfo);
        AwakerRepository.get().updateLocalRealm(userInfoRealm);
    }

    public static void clearUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put(UserInfoRealm.ID, UserInfoRealm.ID_VALUE);
        AwakerRepository.get().deleteLocalRealm(UserInfoRealm.class, map);
    }

    public static void setOpenIdToLocal(String openId) {
        MyApplication.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .edit()
                .putString(OPEN_ID, openId)
                .apply();
    }

    public static void setUserNameToLocal(String userName) {
        MyApplication.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_NAME, userName)
                .apply();
    }
}
