package com.mls.baseProject.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mls.baseProject.R;
import com.mls.baseProject.application.AppContext;


import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by CXX on 2016/8/2
 */
public class GlideImageLoaderUtil {

    /**
     * 加载图片默认模式，使用全局的Context
     *
     * @param url       图片的url地址
     * @param ivContent 需要展示的ImageView
     */
    public static void loadImageFromUrl(String url, ImageView ivContent) {
        Glide.with(AppContext.getApplication())
                .load(url)
                .error(R.drawable.default_pic_code)   //加载失败的图片
                .placeholder(R.drawable.default_pic_code)   //加载中的占位符
                    .override(500,500)
                /**
                 * all:缓存源资源和转换后的资源
                 none:不作任何磁盘缓存

                 source:缓存源资源

                 result：缓存转换后的资源  //默认模式
                 */
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //硬盘缓存策略
                .thumbnail(0.1f)  // 用原图的1/10作为缩略图
                .into(ivContent);
    }

    /**
     * 加载图片默认模式，使用全局的Context
     *
     * @param url       图片的url地址
     * @param ivContent 需要展示的ImageView
     */
    public static void loadImageFromUrl(String url, ImageView ivContent, int imgDefault) {
        Glide.with(AppContext.getApplication())
                .load(url)
                .error(imgDefault)   //加载失败的图片
                .placeholder(imgDefault)   //加载中的占位符
                /**
                 * all:缓存源资源和转换后的资源
                 none:不作任何磁盘缓存

                 source:缓存源资源

                 result：缓存转换后的资源  //默认模式
                 */
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //硬盘缓存策略
                .into(ivContent);
    }

    public static void loadImageFromUrlNo(String url, ImageView ivContent) {
        Glide.with(AppContext.getApplication())
                .load(url)
                /**
                 * all:缓存源资源和转换后的资源
                 none:不作任何磁盘缓存

                 source:缓存源资源

                 result：缓存转换后的资源  //默认模式
                 */
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //硬盘缓存策略
                .into(ivContent);
    }

    /**
     * 加载图片默认模式，使用全局的Context
     *
     * @param url       图片的url地址
     * @param ivContent 需要展示的ImageView
     */
    public static void loadImageFromUrl(Context context, String url, ImageView ivContent, int imgDefault) {
        Glide.with(context)
                .load(url)
                .error(imgDefault)   //加载失败的图片
                .placeholder(imgDefault)   //加载中的占位符
                /**
                 * all:缓存源资源和转换后的资源
                 none:不作任何磁盘缓存

                 source:缓存源资源

                 result：缓存转换后的资源  //默认模式
                 */
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //硬盘缓存策略
                .thumbnail(0.1f)  // 用原图的1/10作为缩略图
                .into(ivContent);
    }

    /**
     * 加载图片默认模式，使用全局的Context
     *
     * @param url       图片的url地址
     * @param ivContent 需要展示的ImageView
     */
    public static void loadImageFromUrlRound(String url, ImageView ivContent, int defauatImg, int round) {
        Glide.with(AppContext.getApplication())
                .load(url)
                .error(defauatImg)   //加载失败的图片
                .placeholder(defauatImg)   //加载中的占位符
                /**
                 * all:缓存源资源和转换后的资源
                 none:不作任何磁盘缓存

                 source:缓存源资源

                 result：缓存转换后的资源  //默认模式
                 */
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //硬盘缓存策略
                .fitCenter()
                .bitmapTransform(new RoundedCornersTransformation(AppContext.getApplication(), round, 0, RoundedCornersTransformation.CornerType.ALL))
//                .thumbnail(0.1f)  // 用原图的1/10作为缩略图
                .into(ivContent);
    }

    /**
     * 加载圆形图标
     *
     * @param url
     * @param ivContent
     * @param defauat
     */
    public static void loadImageFromUrlCircle(String url, ImageView ivContent, int defauat) {
        Glide.with(AppContext.getApplication())
                .load(url)
                .error(defauat)   //加载失败的图片
                .placeholder(defauat)   //加载中的占位符
                /**
                 * all:缓存源资源和转换后的资源
                 none:不作任何磁盘缓存

                 source:缓存源资源

                 result：缓存转换后的资源  //默认模式
                 */
                .diskCacheStrategy(DiskCacheStrategy.RESULT)  //硬盘缓存策略
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(AppContext.getApplication()))
//                .thumbnail(0.1f)  // 用原图的1/10作为缩略图
                .into(ivContent);
    }

    /**
     * 加载图片使用自定义的Context
     *
     * @param context   当前的Activity
     * @param url       图片地址
     * @param ivContent 需要展示的ImageView
     */
    public static void loadImageFromUrl(Context context, String url, ImageView ivContent) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_pic_code)   //加载失败的图片
                .placeholder(R.drawable.ic_moments_banner_bg)   //加载中的占位符
                .centerCrop()
                .thumbnail(0.1f)  // 用原图的1/10作为缩略图
                .into(ivContent);
    }

    /**
     * 加载图片使用自定义Context，并且定义加载优先级
     *
     * @param context   当前的Activity
     * @param url       图片的url地址
     * @param ivContent 需要展示的ImageView
     * @param priority  加载的优先级
     */
    public static void loadImageFromUrl(Context context, String url, ImageView ivContent, Priority priority) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_pic_code)   //加载失败的图片
                .placeholder(R.drawable.default_pic_code)   //加载中的占位符
                .centerCrop()
                .priority(priority)
                .thumbnail(0.1f)  // 用原图的1/10作为缩略图
                .into(ivContent);
    }
    /**
     * 缩放图像
     现在，对于任何图像操作，调整大小真的能让长宽比失真并且丑化图像显示。在你大多数的使用场景中，你想要避免发生这种情况。
     Glide 提供了一般变化去处理图像显示。提供了两个标准选项：centerCrop 和 fitCenter。
     CenterCrop
     CenterCrop()是一个裁剪技术，即缩放图像让它填充到 ImageView 界限内并且侧键额外的部分。ImageView

     FitCenter
     fitCenter() 是裁剪技术，即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView。

     Priority (优先级)枚举
     这个枚举给了四个不同的选项，下面是按照递增priority(优先级)的列表：
     Priority.LOW
     Priority.NORMAL
     Priority.HIGH
     Priority.IMMEDIATE
     */


    //加載方式
    /**
     * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
     *load assets资源：load("file:///android_asset/f003.gif")
     *load raw资源：load("android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
     *load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
     *load ContentProvider资源：load("content://media/external/images/media/139469")
     *load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
     *load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
     */
}
