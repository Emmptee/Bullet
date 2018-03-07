package com.souha.bullet.news.viewmodel;

import android.arch.lifecycle.MutableLiveData;


import com.souha.bullet.Utils.LogUtils;
import com.souha.bullet.base.viewmodel.BaseListViewModel;
import com.souha.bullet.data.Comment;
import com.souha.bullet.data.Header;
import com.souha.bullet.data.NewDetail;
import com.souha.bullet.db.entity.CommentEntity;
import com.souha.bullet.network.EmptyConsumer;
import com.souha.bullet.network.ErrorConsumer;
import com.souha.bullet.news.listener.SendCommentListener;
import com.souha.bullet.source.AwakerRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NewDetailViewModel extends BaseListViewModel {

    private static final String TAG = NewDetailViewModel.class.getSimpleName();

    public Header header = new Header();

    private String newId;

    private SendCommentListener listener;

    private MutableLiveData<NewDetail> newDetailLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> commentListLiveData = new MutableLiveData<>();

    private NewDetail newDetail;
    private List<Comment> commentList = new ArrayList<>();

    private Disposable localDisposable;
    private Disposable localCommentDisposable;

    public NewDetailViewModel(String newId, SendCommentListener listener) {
        this.newId = newId;

        this.listener = listener;

        newDetailLiveData.setValue(null);
        commentListLiveData.setValue(null);
    }

    public void initHeader(String title, String url) {
        header.title = title;
        header.url = url;
    }

    public void initLocalCommentList(String id) {
        localCommentDisposable = AwakerRepository.get().loadCommentEntity(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalCommentList doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalCommentList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalCommentList(CommentEntity localCommentList) {
        if (localCommentList != null && commentList.isEmpty()) {
            commentList.addAll(localCommentList.commentList);
            commentListLiveData.setValue(commentList);
        }
        localCommentDisposable.dispose();
    }

    public void initLocalNewDetail(String id) {
        localDisposable = AwakerRepository.get().loadNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalNewDetail doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalNewDetail)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalNewDetail(NewDetail localNewDetail) {
        if (localNewDetail != null && newDetail == null) {
            newDetail = localNewDetail;
            newDetailLiveData.setValue(newDetail);
        }
        localDisposable.dispose();
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getNewDetail(TOKEN, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> setRefreshDataDoOnNext(httpResult.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setRefreshDataDoOnNext(NewDetail remoteNewDetail) {
        if (remoteNewDetail != null) {
            newDetail = remoteNewDetail;
            newDetailLiveData.setValue(newDetail);

            setNewDetailLocalDb(newDetail);
        }
    }

    private void setNewDetailLocalDb(NewDetail newDetail) {
        Flowable.create(e -> {
            AwakerRepository.get().insertNewsDetail(newDetail);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setNewDetailLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void getHotCommentList() {
        AwakerRepository.get().getUpNewsComments(TOKEN, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "remote doOnError: " + throwable.toString()))
                .doOnNext(result -> setCommentListDoOnNext(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setCommentListDoOnNext(List<Comment> comments) {
        if (comments != null) {
            commentList.clear();
            commentList.addAll(comments);

            commentListLiveData.setValue(commentList);

            setCommentListLocalDb(comments);
        }
    }

    private void setCommentListLocalDb(List<Comment> comments) {
        Flowable.create(e -> {
            CommentEntity commentEntity = new CommentEntity(newId, comments);
            AwakerRepository.get().insertCommentEntity(commentEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setNewDetailLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void sendNewsComment(String newId, String content, String open_id,
                                String pid) {
        disposable.add(AwakerRepository.get().sendNewsComment(TOKEN, newId, content, open_id, pid)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "remote doOnError: " + throwable.toString()))
                .doOnNext(result -> listener.sendCommentSuc())
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public MutableLiveData<NewDetail> getNewDetailLiveData() {
        return newDetailLiveData;
    }

    public MutableLiveData<List<Comment>> getCommentListLiveData() {
        return commentListLiveData;
    }
}
