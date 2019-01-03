package com.mls.baseProject.http;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.google.gson.Gson;
import com.mls.baseProject.application.ActivityManager;
import com.mls.baseProject.application.AppContext;
import com.mls.baseProject.constant.Common;
import com.mls.baseProject.constant.PrefConst;
import com.mls.baseProject.constant.UserPre;
import com.mls.baseProject.entity.response.common.CommResponse;
import com.mls.baseProject.event.NotifyEvent;
import com.mls.baseProject.fragment.BaseFragment;
import com.mls.baseProject.ui.BaseActivity;

import com.mls.baseProject.util.LogUtil;
import com.mls.baseProject.util.NetworkUtil;
import com.mls.baseProject.util.SaveFileUtil;
import com.mls.baseProject.util.UIUtils;
import com.mls.baseProject.widget.LoadingDialog;
import com.mls.baseProject.widget.MessageDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;

/**
 * Created by CXX on 2016/8/29.
 */
public abstract class MySubscriber<T> extends Subscriber<T> {
    private LoadingDialog loadingDialog;
    private boolean messageIsShow;

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        dissMissLoadingDialog();
        e.printStackTrace();
        final Activity activity = ActivityManager.getActivityManager().currentActivity();
        removeLodingView(activity);
        if (!NetworkUtil.hasNetwork(UIUtils.getContext())) {
            AppContext.showToast("网络错误");
            error(Common.ERROR_NET);
            return;
        }

        remmoveNetErrorView(activity);


        if (e instanceof java.net.SocketTimeoutException) {
            AppContext.showLongToast("连接超时");
            this.error(Common.APP_TIME_OUT);
            return;
        }
        this.error(Common.APP_ERROR);

    }

    protected abstract void error(int errorCode);


    @Override
    public void onNext(T o) {
        dissMissLoadingDialog();
        CommResponse commResponse = (CommResponse) o;
        final Activity activity = ActivityManager.getActivityManager().currentActivity();
        removeLodingView(activity);
        remmoveNetErrorView(activity);
        LogUtil.d("onNext:" + new Gson().toJson(commResponse));
        if (commResponse.getResultCode().equals(Common.SUCCESS)) {
            if (activity instanceof BaseActivity) {
//                List<Fragment> fragments = ((BaseActivity) activity).getSupportFragmentManager().getFragments();
//                if (fragments != null && fragments.size() > 0) {
//                    Fragment fragment1 = fragments.get(0);
//                    if (fragment1 instanceof BaseFragment) {
//                        ((BaseFragment) fragment1).removeEmptyView();
//                    }
//
//                }
            }
            onSuccess((T) o);
        } else {
            if (commResponse.getResultCode().equals(Common.NO_DATA)) {
                error(Common.NO_DATA_ERRO);
//                AppContext.showToast("暂无数据");
            } else if (commResponse.getResultCode().equals(Common.INVALID_TOKEN)) {
                UserPre.logout();
                try {
                    SaveFileUtil.saveToSDCard("tokenInvalid.txt", "已失效token" + UserPre.getToken() + "Response" + commResponse);
                    Logger.e("" + "Response" + commResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (activity.toString().contains("UILogin")) {
                    return;
                }
                if (activity.toString().contains("UISplash")) {
                    return;
                }
                if (!messageIsShow) {
                    messageIsShow = true;

                    //TODO 用户登录失效需要清除用户数据
                    MessageDialog messageDialog = new MessageDialog(activity);
                    messageDialog.getDialog("登录提醒", "登录失效，请重新登陆。");
                    messageDialog.seteditDialogListener(new MessageDialog.MessageDialogListener() {
                        @Override
                        public void sure() {
                            //TODO 移除token，回到登录界面
                            NotifyEvent notifyEvent = new NotifyEvent();
                            notifyEvent.setValue(NotifyEvent.INVALID_TOKEN_SURE);
                            EventBus.getDefault().post(notifyEvent);
                            Intent intent = new Intent();
                            intent.setAction("com.mls.antique.ui.login.UILogin");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            AppContext.getApplication().startActivity(intent);
                            messageIsShow = false;
                            activity.finish();


                        }

                        @Override
                        public void cancel() {
                            //TODO 当登录失效点击取消按钮需要做的处理
                            NotifyEvent notifyEvent = new NotifyEvent();
                            notifyEvent.setValue(NotifyEvent.INVALID_TOKEN_CANCLE);
                            EventBus.getDefault().post(notifyEvent);
                            messageIsShow = false;
                            AppContext.getPreUtils().remove(PrefConst.PRE_USER_TOKEN);
                            System.exit(0);
                        }
                    });
                } else {
                    return;
                }
            } else {
                AppContext.showToast(commResponse.getErrorMsg());
//                AppContext.showToast("服务器错误");
                error(Common.APP_ERROR);
            }

        }


    }

    private void remmoveNetErrorView(Activity activity) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).removeNetErrorView();
            List<Fragment> fragments = ((BaseActivity) activity).getSupportFragmentManager().getFragments();
            if (fragments != null && fragments.size() > 0) {
                Fragment fragment1 = fragments.get(0);
                if (fragment1 instanceof BaseFragment) {
                    for (int i = 0; i < fragments.size(); i++) {
                        if (fragments.get(i) instanceof BaseFragment) {
                            BaseFragment fragment = (BaseFragment) fragments.get(i);
                            fragment.removeNetErrorView();
                        }
                    }
                }
            }
        }
    }

    private void removeLodingView(Activity activity) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).removeLodingView();
            List<Fragment> fragments = ((BaseActivity) activity).getSupportFragmentManager().getFragments();
            if (fragments != null && fragments.size() > 0) {
                Fragment fragment1 = fragments.get(0);
                if (fragment1 instanceof BaseFragment) {
                    for (int i = 0; i < fragments.size(); i++) {
                        if (fragments.get(i) instanceof BaseFragment) {
                            BaseFragment fragment = (BaseFragment) fragments.get(i);
                            fragment.removeLodingView();
                        }
                    }
                }

            }
        }
    }

    protected abstract void onSuccess(T response);

    public void showLoadingDialog(Activity activity, String info) {
        loadingDialog = new LoadingDialog(activity);
        loadingDialog.getDialog(info);
    }

    public void dissMissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dissMissDialog();
        }
    }
}
