package com.souha.bullet.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.souha.bullet.data.BannerItem;
import com.souha.bullet.data.NewDetail;
import com.souha.bullet.data.News;
import com.souha.bullet.db.converter.RoomDataConverter;
import com.souha.bullet.db.dao.BannerDao;
import com.souha.bullet.db.dao.CommentListDao;
import com.souha.bullet.db.dao.NewDetailDao;
import com.souha.bullet.db.dao.NewsDao;
import com.souha.bullet.db.dao.SpecialListDao;
import com.souha.bullet.db.entity.CommentEntity;
import com.souha.bullet.db.entity.SpecialListEntity;


@Database(entities = {News.class, SpecialListEntity.class, BannerItem.class, NewDetail.class,
        CommentEntity.class},
        version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "awaker-db";

    private static AppDatabase INSTANCE;

    public abstract NewsDao newsDao();

    public abstract SpecialListDao specialListDao();

    public abstract BannerDao bannerDao();

    public abstract NewDetailDao newDetailDao();

    public abstract CommentListDao commentListDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();


    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .build();
                    INSTANCE.updateDatabaseCreated(context);
                }
            }
        }
        return INSTANCE;
    }

    private void updateDatabaseCreated(Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            isDatabaseCreated.postValue(true);
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}
