package com.mls.baseProject.util;

import android.app.Activity;

import com.mls.baseProject.widget.LoadingDialog;

/**
 * Created by gefei on 2018/3/24.
 */

public class DialogUtil {
    private static LoadingDialog loadingDialog;

    public static void showLoadingDialog(Activity activity, String info) {
        loadingDialog = new LoadingDialog(activity);
        if (!loadingDialog.isShowing()) {
            loadingDialog.getDialog(info);
        }

    }

    public static void dissMissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dissMissDialog();
        }
    }
}
