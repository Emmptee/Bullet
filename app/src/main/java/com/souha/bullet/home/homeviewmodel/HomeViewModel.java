package com.souha.bullet.home.homeviewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.souha.bullet.R;
import com.souha.bullet.Utils.LogUtils;
import com.souha.bullet.Utils.ResUtils;
import com.souha.bullet.base.viewmodel.BaseViewModel;
import com.souha.bullet.data.BannerItem;
import com.souha.bullet.network.EmptyConsumer;
import com.souha.bullet.network.ErrorConsumer;
import com.souha.bullet.network.HttpResult;
import com.souha.bullet.source.AwakerRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shidongfang on 2018/2/28.
 */

public class HomeViewModel extends BaseViewModel {
    private static final String TAG = HomeViewModel.class.getName();

    private String advType = ResUtils.getString(R.string.adv_type);
    private List<BannerItem> bannerItemList = new ArrayList<>();
    private MutableLiveData<List<BannerItem>> bannerLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public HomeViewModel() {

    }

    public void initLocalBanners() {
        localDisposable = AwakerRepository.get().loadAllBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "initLocalBanner doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalBanners)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalBanners(List<BannerItem> banners) {
        if (banners != null && bannerItemList.isEmpty()) {
            bannerLiveData.setValue(banners);
        }
        localDisposable.dispose();
    }

    public void getBanner() {
        AwakerRepository.get().getBanner(TOKEN, advType)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "getBanner doOnError:" + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .map(HttpResult::getData)
                .doOnNext(this::refreshDataOnNext)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void refreshDataOnNext(List<BannerItem> bannerItems) {
        bannerItemList.clear();
        bannerItemList.addAll(bannerItems);
        bannerLiveData.setValue(bannerItemList);
        //save news to local db
        setBannerToLocalDb(bannerItems);
    }

    private void setBannerToLocalDb(List<BannerItem> bannerList) {
        Flowable.create(e -> {
            AwakerRepository.get().insertAllBanners(bannerList);
            e.onComplete();
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "setBannerToLocalDb doOnError:" + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public MutableLiveData<List<BannerItem>> getBannerLiveData() {
        return bannerLiveData;
    }
}
