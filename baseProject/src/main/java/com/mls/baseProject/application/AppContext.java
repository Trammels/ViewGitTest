package com.mls.baseProject.application;

import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mls.baseProject.R;
import com.mls.baseProject.constant.PrefConst;
import com.mls.baseProject.util.PreferenceUtil;
import com.uuzuche.lib_zxing.DisplayUtil;


/**
 * Created by CXX on 2015/9/24.
 */
public class AppContext extends MultiDexApplication {
    private static AppContext mInstance;

    private static AppContext instance;
    //
    private static AppContext mContext = null;
    private static Handler mMainThreadHandler = null;
    //
    private static Looper mMainThreadLooper = null;
    //
    private static Thread mMainThead = null;
    //
    private static int mMainTheadId;
    private static Toast mToast;
    public static boolean isDebug = true;


    public static AppContext getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }


    public static AppContext getInstance() {
        return instance;
    }

    public static void showToast(String msg) {
        if (null == mToast) {
            View toastView = LayoutInflater.from(mInstance).inflate(R.layout.view_toast, null);
            mToast = new Toast(mInstance);
            mToast.setView(toastView);
            TextView toastContent = (TextView) toastView.findViewById(R.id.message);
            toastContent.setText(msg);
        } else {
            TextView toastContent = (TextView) (mToast.getView().findViewById(R.id.message));
            toastContent.setText(msg);
        }
        mToast.show();
    }

    public static void showLongToast(String msg) {
        if (null == mToast) {
            View toastView = LayoutInflater.from(mInstance).inflate(R.layout.view_toast, null);
            mToast = new Toast(mInstance);
            mToast.setView(toastView);
            TextView toastContent = (TextView) toastView.findViewById(R.id.message);
            toastContent.setText(msg);
        } else {
            TextView toastContent = (TextView) (mToast.getView().findViewById(R.id.message));
            toastContent.setText(msg);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static PreferenceUtil getPreUtils() {
        return new PreferenceUtil(mInstance, PrefConst.PREFERENCE_FILENAME, MODE_PRIVATE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        instance = this;
        this.mContext = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        this.mMainTheadId = android.os.Process.myTid();
        init();
        initDisplayOpinion();
    }

    public void init() {

    }



    public void showToast(int resId) {
        showToast(getString(resId));
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }


}
