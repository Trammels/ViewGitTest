package gefei.com.viewdemo.http;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.mls.baseProject.application.ActivityManager;
import com.mls.baseProject.application.AppContext;
import com.mls.baseProject.constant.Common;
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
//        AppContext.showLongToast(e.getMessage());
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

        this.error(Common.APP_ERROR);
        if (e instanceof java.net.SocketTimeoutException) {
            AppContext.showLongToast("连接超时,请重新选择");
            NotifyEvent notifyEvent = new NotifyEvent();
//            notifyEvent.setValue(NotifyEvent.TIMEOUTEXCEPTION);
            EventBus.getDefault().post(notifyEvent);


//            this.error(Common.APP_TIME_OUT);
//            return;
        }


    }

    protected abstract void error(int errorCode);

    protected <T> T getGson(String data, Class<T> tClass) {
        T result = new Gson().fromJson(data, tClass);
        return result;
    }

    @Override
    public void onNext(T o) {
        dissMissLoadingDialog();
        CommResponse commResponse = (CommResponse) o;
        final Activity activity = ActivityManager.getActivityManager().currentActivity();
        removeLodingView(activity);
        remmoveNetErrorView(activity);
        LogUtil.d("onNext:" + new Gson().toJson(commResponse));

        if (commResponse.getState().equals(Common.SUCCESS)) {
            // TODO: 2018/7/27 替换全文本，规则不知道，先注释
//            String string = new Gson().toJson(o).replaceAll("二采区", "一采区");
//            o =  new Gson().fromJson(string, (Class<T>)o.getClass());
            onSuccess((T) o);
        } else {
            if (commResponse.getState().equals(Common.NO_DATA)) {
                error(Common.NO_DATA_ERRO);
//                AppContext.showToast("暂无数据");
            } else if (commResponse.getState().equals(Common.INVALID_TOKEN)) {
                UserPre.logout();
                try {
                    SaveFileUtil.saveToSDCard("tokenInvalid.txt", "已失效token" + UserPre.getToken() + "Response" + commResponse.toString());
                    Logger.e("" + "Response" + commResponse);
                } catch (Exception e) {
                    e.printStackTrace();
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
//                            notifyEvent.setValue(NotifyEvent.INVALID_TOKEN_SURE);
                            EventBus.getDefault().post(notifyEvent);
                            messageIsShow = false;


                        }

                        @Override
                        public void cancel() {
                            //TODO 当登录失效点击取消按钮需要做的处理
                            NotifyEvent notifyEvent = new NotifyEvent();
//                            notifyEvent.setValue(NotifyEvent.INVALID_TOKEN_CANCLE);
                            EventBus.getDefault().post(notifyEvent);
                            messageIsShow = false;
                        }
                    });
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
