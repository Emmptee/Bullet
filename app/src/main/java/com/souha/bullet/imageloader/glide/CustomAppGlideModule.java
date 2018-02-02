package com.souha.bullet.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by shidongfang on 2018/2/2.
 */
@GlideModule
public class CustomAppGlideModule extends AppGlideModule {
    /**设置内存缓存大小10M
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(20*1024*1024));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
