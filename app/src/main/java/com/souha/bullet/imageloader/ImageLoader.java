package com.souha.bullet.imageloader;

import com.souha.bullet.imageloader.glide.GlideImpl;

/**
 * Created by shidongfang on 2018/2/2.
 */

public final class ImageLoader {
    private static IImageLoader iImageLoader;

    public ImageLoader() {

    }

    public static IImageLoader get() {
        if (iImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (iImageLoader == null) {
                    iImageLoader = new GlideImpl();
                }
            }
        }
        return iImageLoader;
    }
}
