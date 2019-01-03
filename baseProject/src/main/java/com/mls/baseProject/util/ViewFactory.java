package com.mls.baseProject.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.mls.baseProject.R;


/**
 * ImageView创建工厂
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @param
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);

        GlideImageLoaderUtil.loadImageFromUrl(context, url, imageView, R.drawable.home_banner_load);
//		ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }

    public static ImageView getImageViewCenter(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner_center, null);

        GlideImageLoaderUtil.loadImageFromUrl(context, url, imageView, R.drawable.home_banner_load);
//		ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }
}
