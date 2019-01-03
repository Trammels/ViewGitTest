package com.mls.baseProject.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mls.baseProject.application.AppContext;

import java.lang.reflect.Method;
import java.util.List;


public class UIUtils {


    public static Context getContext() {
        return AppContext.getApplication();
    }

    public static Thread getMainThread() {
        return AppContext.getMainThread();
    }

    public static long getMainThreadId() {
        return AppContext.getMainThreadId();
    }

    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static Handler getHandler() {
        return AppContext.getMainThreadHandler();
    }

    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    public static View inflate(Context context, int resId) {
        return LayoutInflater.from(context).inflate(resId, null);
    }

    public static Resources getResources() {

        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    public static void startActivity(Intent intent) {
//		MySwipeBackActivity activity = MySwipeBackActivity.getForegroundActivity();
//		if(activity != null){
//			activity.startActivity(intent);
//		}else{
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			getContext().startActivity(intent);
//		}
    }

    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static void showToast(String str) {
//		MySwipeBackActivity frontActivity = MySwipeBackActivity.getForegroundActivity();
//		if (frontActivity != null) {
//			Toast.makeText(frontActivity, str, Toast.LENGTH_LONG).show();
//		}
    }


    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

//    /**
//     * 获取屏幕高度
//     */
//    public static int getScreenHeight(Activity activity) {
//        WindowManager windowManager = activity.getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        return display.getHeight();
//    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     */
    public static int getstatusBarHeight(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;

    }

    /**
     * 打开输入法
     *
     * @param editContent EditText
     */
    public static void openInputService(final EditText editContent) {
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) editContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editContent, 0);
            }
        }, 500);
    }

    /**
     * 关闭输入法
     *
     * @param activity 当前Activity
     */
    public static void closeInputServer(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }


    }
    public static void KeyBoardCancle(Dialog dialog) {
        View view =dialog.getWindow().peekDecorView();//注意：这里要根据EditText位置来获取
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) AppContext.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
     *
     * @param view
     * @param isHeight
     * @return
     */
    public static int getViewHeightOrWidth(View view, boolean isHeight) {
        int result;
        if (view == null) return 0;
        if (isHeight) {
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(h, 0);
            result = view.getMeasuredHeight();
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(0, w);
            result = view.getMeasuredWidth();
        }
        return result;
    }


    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        android.app.ActivityManager activityManager = (android.app.ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(30);
        if (!(runningServices.size() > 0)) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * @return 获取屏幕原始尺寸高度，包括虚拟功能键高度
     */
    public static int getTotalHeight(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * @param context
     * @return 获取屏幕内容高度不包括虚拟按键
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    public static int getVersionCode(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        int version = packInfo.versionCode;
        return version;
    }

}
