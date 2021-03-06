package com.souha.bullet;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.crashlytics.android.Crashlytics;
import com.souha.bullet.Utils.ConstantUtils;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by shidongfang on 2018/2/7.
 */

public class MyApplication extends Application {
    private static final String AWAKER_DB = "awakerDB";
    private static final int VERSION_CODE = 0;
    private static Application INSTANCE;
    public static Application get(){
        return INSTANCE;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Utils.init(MyApplication.get());
        initRealm();
        Account.get().initUserInfo();
        if (ConstantUtils.isReleaseBuild()){
            Fabric.with(this,new Crashlytics());
        }
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(AWAKER_DB)
                .schemaVersion(VERSION_CODE)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
