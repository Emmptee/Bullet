package com.souha.bullet.imageloader.glide;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.souha.bullet.R;
import com.souha.bullet.imageloader.IImageLoader;

/**
 * Created by shidongfang on 2018/2/2.
 */

public class GlideImpl implements IImageLoader {
    private static final String KEY_MEMORY = "com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout";
    private static final float SIZE_MULTIPLIER = 0.3f;
    private static final int TIMEOUT_MS = 16000;
    private  static Option<Integer> TIMEOUT_OPTION = Option.memory(KEY_MEMORY,TIMEOUT_MS);
private DrawableTransitionOptions normalTransitionOptions = new DrawableTransitionOptions().crossFade();

    @Override
    public void load(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .set(TIMEOUT_OPTION,TIMEOUT_MS)
                .transition(normalTransitionOptions)
                .placeholder(R.drawable.image_mark)
                .error(R.drawable.image_mark)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, String url, RequestListener<Drawable> listener) {

    }

    @Override
    public void load(ImageView imageView, int resId, RequestListener<Drawable> listener) {

    }

    @Override
    public void loadThumb(ImageView imageView, String url) {

    }

    @Override
    public void loadCropCircle(ImageView imageView, String url) {

    }

    @Override
    public void loadCropCircle(ImageView imageView, int resId) {

    }
}
