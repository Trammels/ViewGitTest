package com.mls.baseProject.util;

import com.mls.baseProject.application.AppContext;

/**
 * Description: 打印日志帮助类
 */
public class LogUtil {

    private static final String TAG = "SCM";
    private static boolean isDebug = AppContext.isDebug;

    public static void v(String msg) {
        if (isDebug)
            android.util.Log.v(TAG, buildMessage(msg));
    }

    public static void v(String msg, Throwable thr) {
        if (isDebug)
            android.util.Log.v(TAG, buildMessage(msg), thr);
    }

    public static void v(String Tag, String msg) {
        if (isDebug)
            android.util.Log.v(Tag, buildMessage(msg));
    }

    public static void d(String msg) {
        if (isDebug)
            android.util.Log.d(TAG, buildMessage(msg));
    }

    public static void d(String TAG, String msg) {
        if (isDebug)
            android.util.Log.d(TAG, buildMessage(msg));
    }

    public static void d(String msg, Throwable thr) {
        if (isDebug)
            android.util.Log.d(TAG, buildMessage(msg), thr);
    }

    public static void i(String msg) {
        if (isDebug)
            android.util.Log.i(TAG, buildMessage(msg));
    }

    public static void i(String TAG, String msg) {
        if (isDebug)
            android.util.Log.i(TAG, buildMessage(msg));
    }

    public static void i(String msg, Throwable thr) {
        if (isDebug)
            android.util.Log.i(TAG, buildMessage(msg), thr);
    }

    public static void e(String msg) {
        if (isDebug)
            android.util.Log.e(TAG, buildMessage(msg));
    }

    public static void w(String msg) {
        if (isDebug)
            android.util.Log.w(TAG, buildMessage(msg));
    }

    public static void w(String Tag, String msg) {
        if (isDebug)
            android.util.Log.w(Tag, buildMessage(msg));
    }

    public static void w(String Tag, String msg, Throwable thr) {
        if (isDebug)
            android.util.Log.w(Tag, buildMessage(msg), thr);
    }

    public static void w(String msg, Throwable thr) {
        if (isDebug)
            android.util.Log.w(TAG, buildMessage(msg), thr);
    }

    public static void w(Throwable thr) {
        if (isDebug)
            android.util.Log.w(TAG, buildMessage(""), thr);
    }

    public static void e(String msg, Throwable thr) {
        if (isDebug)
            android.util.Log.e(TAG, buildMessage(msg), thr);
    }

    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];

        return new StringBuilder()
                .append(caller.getClassName())
                .append(".")
                .append(caller.getMethodName())
                .append("(): ")
                .append(msg).toString();
    }
}
