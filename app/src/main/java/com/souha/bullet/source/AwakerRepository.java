package com.souha.bullet.source;



import android.app.Application;

import com.souha.bullet.MyApplication;
import com.souha.bullet.data.BannerItem;
import com.souha.bullet.data.Comment;
import com.souha.bullet.data.NewDetail;
import com.souha.bullet.data.News;
import com.souha.bullet.data.Special;
import com.souha.bullet.data.SpecialDetail;
import com.souha.bullet.data.UserInfo;
import com.souha.bullet.db.AppDatabase;
import com.souha.bullet.db.entity.CommentEntity;
import com.souha.bullet.db.entity.SpecialListEntity;
import com.souha.bullet.network.AwakerClient;
import com.souha.bullet.network.HttpResult;
import com.souha.bullet.source.local.ILocalDataSource;
import com.souha.bullet.source.local.LocalDataSourceImpl;
import com.souha.bullet.source.remote.IRemoteDataSource;
import com.souha.bullet.source.remote.RemoteDataSourceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by ruzhan
 */

public final class AwakerRepository implements ILocalDataSource, IRemoteDataSource {

    private static AwakerRepository INSTANCE;

    private ILocalDataSource localDataSource;
    private IRemoteDataSource remoteDataSource;

    private AppDatabase appDatabase;

    private AwakerRepository() {
        localDataSource = new LocalDataSourceImpl();
        remoteDataSource = new RemoteDataSourceImpl(AwakerClient.get());

        appDatabase = AppDatabase.get(MyApplication.get());
    }

    public static AwakerRepository get() {
        if (INSTANCE == null) {
            synchronized (AwakerRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AwakerRepository();
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<CommentEntity> loadCommentEntity(String id) {
        return appDatabase.commentListDao().loadCommentEntity(id);
    }

    public void insertCommentEntity(CommentEntity commentEntity) {
        appDatabase.commentListDao().insertCommentEntity(commentEntity);
    }

    public Flowable<NewDetail> loadNewsDetail(String id) {
        return appDatabase.newDetailDao().loadNewsDetail(id);
    }

    public void insertNewsDetail(NewDetail newDetail) {
        appDatabase.newDetailDao().insertNewsDetail(newDetail);
    }

    public Flowable<List<BannerItem>> loadAllBanners() {
        return appDatabase.bannerDao().loadAllBanners();
    }

    public void insertAllBanners(List<BannerItem> bannerItems) {
        appDatabase.bannerDao().insertAllBanners(bannerItems);
    }

    public Flowable<SpecialListEntity> loadSpecialListEntity(String id) {
        return appDatabase.specialListDao().loadSpecialListEntity(id);
    }

    public void insertAll(SpecialListEntity specialListEntity) {
        appDatabase.specialListDao().insertAll(specialListEntity);
    }

    public Flowable<List<News>> loadNewsList() {
        return appDatabase.newsDao().loadNewsList();
    }

    public void insertNewsList(List<News> newsList) {
        appDatabase.newsDao().insertNewsList(newsList);
    }

    @Override
    public Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map) {
        return localDataSource.getLocalRealm(clazz, map);
    }

    @Override
    public void updateLocalRealm(RealmModel realmModel) {
        localDataSource.updateLocalRealm(realmModel);
    }

    @Override
    public void deleteLocalRealm(Class clazz, Map<String, String> map) {
        localDataSource.deleteLocalRealm(clazz, map);
    }

    @Override
    public void clearAll() {
        localDataSource.clearAll();
    }

    @Override
    public void close() {
        localDataSource.close();
    }

    @Override
    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
        return remoteDataSource.getBanner(token, advType);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
        return remoteDataSource.getNewList(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return remoteDataSource.getSpecialList(token, page, cat);
    }

    @Override
    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return remoteDataSource.getNewDetail(token, newId);
    }

    @Override
    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return remoteDataSource.getSpecialDetail(token, id);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId) {
        return remoteDataSource.getUpNewsComments(token, newId);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getNewsComments(String token, String newId,
                                                               int page) {
        return remoteDataSource.getNewsComments(token, newId, page);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotviewNewsAll(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotNewsAll(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getHotComment(String token) {
        return remoteDataSource.getHotComment(token);
    }

    @Override
    public Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content,
                                                        String open_id, String pid) {
        return remoteDataSource.sendNewsComment(token, newId, content, open_id, pid);
    }

    @Override
    public Flowable<HttpResult<Object>> register(String token, String email, String nickname,
                                                 String password) {
        return remoteDataSource.register(token, email, nickname, password);
    }

    @Override
    public Flowable<UserInfo> login(String token, String username, String password) {
        return remoteDataSource.login(token, username, password);
    }
}
