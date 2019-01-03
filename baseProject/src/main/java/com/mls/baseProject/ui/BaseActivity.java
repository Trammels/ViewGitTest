package com.mls.baseProject.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.mls.baseProject.R;
import com.mls.baseProject.application.ActivityManager;
import com.mls.baseProject.application.AppContext;
import com.mls.baseProject.constant.Common;
import com.mls.baseProject.util.SystemBarTintManager;
import com.mls.baseProject.util.UIUtils;
import com.mls.baseProject.widget.PtrDefaultHeader;
import com.mls.baseProject.widget.PullToLoadMoreListView;
import com.wang.avi.AVLoadingIndicatorView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * 不需要滑动返回的Activity的基类
 * ===========================
 * 本类主要做了以下工作：
 * 1.开启一个新的Activity将Activity放入一个新的栈中，退出时从栈中出栈
 * 2.添加startActivity的统一调用方法
 * 3.添加统一的Toast方法showToast()
 * 4.添加统一的加载中布局需要时调用addLodingView()接口请求成功后会自动移除
 * 5.添加统一的错误布局：网络连接错误，服务端返回错误可自定义
 * 【因为统一处理的原因如果想统一交给父类处理子类的布局文件需要使用FrameLayout命名为fl_main
 * 如果使用linerlayout添加的布局会挤出屏幕】
 * 【如果为网络错误的时候想实现点击重新加载可以直接实现onNetErrorClick（）方法即可】
 * 6.添加统一的刷新和上拉加载布局子类子需要调用addRefresh()即可
 * ============================
 * <p>
 * Created by CXX on 2015/9/24.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener, TakePhoto.TakeResultListener, InvokeListener {
    protected SystemBarTintManager tintManager;

    protected Context mContext;
    public boolean canRefresh = false;

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getTakePhoto().onCreate(bundle);
        StatusBarCompat.setStatusBarColor(this, UIUtils.getColor(R.color.main_color));
        super.onCreate(bundle);
        mContext = this;
        initView();
        initData(bundle);
        ActivityManager.getActivityManager().pushActivity(this);
        registerMp3Receiver();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 当子类需要刷新时 直接调用此方法会生成对应的refresh如果需要上拉加载会生成loadMore方法
     * refresh和loadMore为两个空实现
     *
     * @param ptrMain
     * @param lvContent
     */
    public void addRefresh(PtrFrameLayout ptrMain, final ListView lvContent) {
        PtrDefaultHeader header = new PtrDefaultHeader(this);
        ptrMain.setHeaderView(header);
        ptrMain.addPtrUIHandler(header);
        ptrMain.disableWhenHorizontalMove(true);
        ptrMain.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (canRefresh) {
                    return true;
                }
                if (lvContent == null) {
                    return super.checkCanDoRefresh(frame, content, header);
                }
                int firstVisiblePosition = lvContent.getFirstVisiblePosition();
                View childAt = lvContent.getChildAt(0);
                if (childAt == null) {
                    return false;
                }
                int top = lvContent.getChildAt(0).getTop();
                return (top == 0 && firstVisiblePosition == 0) ? true : false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh(frame);
            }
        });

        if (lvContent instanceof PullToLoadMoreListView) {
            ((PullToLoadMoreListView) lvContent).setOnLoadMoreListener(() -> loadMore());
        }

    }

    /**
     * 下拉刷新
     *
     * @param frame
     */
    public void refresh(PtrFrameLayout frame) {

    }


    /**
     * 上拉加载
     */
    public void loadMore() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().popActivity(this);
        removeLodingView();
        removeNetErrorView();
        if (null != playReceiver) {
            unregisterReceiver(playReceiver);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        UIUtils.closeInputServer(this);
    }

    protected abstract void initView();

    protected abstract void initData(Bundle bundle);

    protected ListView initTitle(String title) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setText(title);
        findViewById(R.id.img_action_left).setOnClickListener(this);
        findViewById(R.id.txt_action_title).setOnClickListener(this);
        return null;
    }

    protected ListView initTitle(int title) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setText(title);
        findViewById(R.id.img_action_left).setOnClickListener(this);
        findViewById(R.id.txt_action_title).setOnClickListener(this);
        return null;
    }

    protected void initTitle(String title, String right) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        TextView txtRight = (TextView) findViewById(R.id.txt_action_right);
        txtTitle.setText(title);
        txtRight.setText(right);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(this);
        txtTitle.setOnClickListener(this);
        findViewById(R.id.img_action_left).setOnClickListener(this);
    }

    protected void initTitle(String title, int drawright) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        TextView txtRight = (TextView) findViewById(R.id.txt_action_right);
        txtTitle.setText(title);
        txtRight.setVisibility(View.GONE);
        ImageView imgRight = (ImageView) findViewById(R.id.img_right);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setOnClickListener(this);
        imgRight.setImageResource(drawright);
        findViewById(R.id.img_action_left).setOnClickListener(this);
    }

    protected void initTitle(String title, int drawleft, String txtRight) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        TextView tvRight = (TextView) findViewById(R.id.txt_action_right);
        txtTitle.setText(title);
        tvRight.setText(txtRight);
        tvRight.setVisibility(View.VISIBLE);
        ImageView imgRight = (ImageView) findViewById(R.id.img_action_left);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setOnClickListener(this);
        imgRight.setImageResource(drawleft);
    }

    protected void initTitle(String title, String content, boolean isShow) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        TextView txtRight = (TextView) findViewById(R.id.txt_action_right);
        txtTitle.setText(title);
        txtRight.setText(content);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(this);
        txtTitle.setOnClickListener(this);
        if (!isShow) {
            findViewById(R.id.img_action_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.img_action_left).setOnClickListener(this);
        }
    }


    protected void initTitle(String title, boolean isShow) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        txtTitle.setText(title);
        txtTitle.setOnClickListener(this);
        if (!isShow) {
            findViewById(R.id.img_action_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.img_action_left).setOnClickListener(this);
        }
    }

    protected void initTitle(int title, boolean isShow) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        txtTitle.setText(title);
        txtTitle.setOnClickListener(this);
        if (!isShow) {
            findViewById(R.id.img_action_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.img_action_left).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_action_left) finish();
    }

    /**
     * @param fromClass
     * @param toClass
     */
    public void startActivity(Context fromClass, Class<?> toClass) {
        startActivity(fromClass, toClass, null);
    }

    /**
     * @param fromClass
     * @param toClass
     * @param bundle
     */
    public void startActivity(Context fromClass, Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        fromClass.startActivity(intent);
    }


    protected void instantiateFrament(int containerId, Fragment fgm,
                                      String fragmentName) {
        try {
            FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction();
            ft.replace(containerId, fgm, fragmentName);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void instantiateFrament(int containerId, Fragment fgm) {
        try {
            FragmentTransaction ft = getSupportFragmentManager()
                        .beginTransaction();
            ft.replace(containerId, fgm);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode) {
        startActivityForResult(fromClass, toClass, requestCode, null);
    }


    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != data) {
            intent.putExtras(data);
        }
        fromClass.startActivityForResult(intent, requestCode);
    }

    public void showToast(String msg) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                AppContext.getInstance().showToast(msg);

            }
        });
    }

    public void showToast(int resId) {
        AppContext.getInstance().showToast(resId);
    }


    View emptyView;

    /**
     * 添加界面为空的布局 根布局要为FrameLayout
     */
    public void addEmptyView() {
        ViewGroup contentView = (ViewGroup) getWindow().getDecorView().findViewById(R.id.fl_content);
        canRefresh = true;
        emptyView = UIUtils.inflate(R.layout.view_empty);
        emptyView.findViewById(R.id.lin_main).setOnClickListener(v -> {

        });
        ((ViewGroup) contentView).addView(emptyView);
    }

    /**
     * 移除界面为空的布局
     */
    public void removeEmptyView() {
        canRefresh = false;
        ViewGroup contentView = (ViewGroup) getWindow().getDecorView().findViewById(R.id.fl_content);
        if (emptyView != null)
            (contentView).removeView(emptyView);
        emptyView = null;
    }

    /**
     * 添加界面为空的布局 根布局要为FrameLayout
     */
    public void addEmptyView(int imgSrc, String content) {
        canRefresh = true;
        ViewGroup contentView = (ViewGroup) getWindow().getDecorView().findViewById(R.id.fl_content);
        canRefresh = true;
        if (emptyView == null) {
            emptyView = UIUtils.inflate(R.layout.view_empty);
            ImageView ivContent = (ImageView) emptyView.findViewById(R.id.img_content);
            TextView tvContent = (TextView) emptyView.findViewById(R.id.tv_content);
            ivContent.setImageResource(imgSrc);
            tvContent.setText(content);
            emptyView.findViewById(R.id.lin_main).setOnClickListener(v -> {

            });
        }
        contentView.addView(emptyView);
    }

    /**
     * 添加界面为空的布局 根布局要为FrameLayout
     */
    public void addEmptyView(int mainView, int imgSrc, String content) {
        canRefresh = true;
        ViewGroup contentView = (ViewGroup) getWindow().getDecorView().findViewById(mainView);
        canRefresh = true;
        ((ViewGroup) contentView).addView(emptyView);
        if (emptyView == null) {
            emptyView = UIUtils.inflate(R.layout.view_empty);
            ImageView ivContent = (ImageView) emptyView.findViewById(R.id.img_content);
            TextView tvContent = (TextView) emptyView.findViewById(R.id.tv_content);
            ivContent.setImageResource(imgSrc);
            tvContent.setText(content);
            emptyView.findViewById(R.id.lin_main).setOnClickListener(v -> {

            });
            contentView.addView(emptyView);
        }

    }

    private View netErrorView;

    /**
     * 添加网络请求错误布局
     */
    public void addNetErrorView() {
        if (netErrorView != null) {
            return;
        }
        View viewById = getWindow().getDecorView();
        netErrorView = UIUtils.inflate(R.layout.view_net_error);
        LinearLayout linMain = (LinearLayout) netErrorView.findViewById(R.id.lin_net_main);
        ((ViewGroup) viewById).addView(netErrorView);
        linMain.setOnClickListener(v -> onNetErrorClick());


    }

    /**
     * 点击网络错误的按钮 空实现交给子类实现
     */
    public void onNetErrorClick() {

    }

    /**
     * 移除网络错误布局
     */
    public void removeNetErrorView() {
        View viewById = getWindow().getDecorView();
        if (netErrorView != null) {
            ((ViewGroup) viewById).removeView(netErrorView);
        }
        netErrorView = null;
    }

    /**
     * 添加错误布局界面
     */
    public void addErrorView(int errorCode) {
        if (errorCode == Common.ERROR_NET) {
            addNetErrorView();
        } else if (errorCode == Common.NO_DATA_ERRO) {
            addEmptyView();
        } else {
            addEmptyView();
        }
    }

    /**
     * 添加错误布局界面
     */
    public void addErrorView(int errorCode, int imgSrc, String content) {
        if (errorCode == Common.ERROR_NET) {
            addNetErrorView();
        } else if (errorCode == Common.NO_DATA_ERRO) {
            addEmptyView(imgSrc, content);
        } else {
            addEmptyView();
        }
    }

    /**
     * 添加错误布局
     *
     * @param errorCode 错误码
     * @param pageSize  当前listview中有多少个数据
     * @param lvContent 需要上拉加载的listview
     */
    public void addErrorView(int errorCode, int pageSize, PullToLoadMoreListView lvContent) {
        if (errorCode == Common.ERROR_NET) {
            addNetErrorView();
        } else if (errorCode == Common.NO_DATA_ERRO) {
            if (pageSize > 0) {
                lvContent.loadMoreComplete();
                lvContent.setHasMore(false);
            } else {
                addEmptyView();
            }

        } else {
            removeLodingView();
            addEmptyView();
        }
    }

    /**
     * 特殊情况 如当获取到数据为第二页返回no_date进行的特殊处理
     *
     * @param errorCode 错误码
     * @param imgSrc    错误提示界面显示的图片
     * @param content   错误提示界面显示错误提醒的文字
     * @param pageSize  每页获取的数据个数
     * @param lvContent 当前的listview
     */
    public void addErrorView(int errorCode, int imgSrc, String content, int pageSize, PullToLoadMoreListView lvContent) {
        if (errorCode == Common.ERROR_NET) {
            addNetErrorView();
        } else if (errorCode == Common.NO_DATA_ERRO) {
            if (pageSize > 0) {
                lvContent.loadMoreComplete();
                lvContent.setHasMore(false);

            } else {
                addEmptyView(imgSrc, content);
            }

        } else {
            addEmptyView();
        }
    }

    public View lodingView;

    /**
     * 添加加载中的布局
     */
    public void addLodingView() {
        View viewById = getWindow().getDecorView();
        View linLoading = viewById.findViewById(R.id.lin_loading);
        lodingView = linLoading;
        if (linLoading == null) {

        } else {
            viewById.findViewById(R.id.lin_loading).setVisibility(View.VISIBLE);
            ((AVLoadingIndicatorView) viewById.findViewById(R.id.avl_loading)).show();
        }

    }


    /**
     * 移除加载中的布局
     */
    public void removeLodingView() {
        View viewById = getWindow().getDecorView();
        View linLoading = viewById.findViewById(R.id.lin_loading);
        if (lodingView == null) {

        } else {
            lodingView.setVisibility(View.GONE);
            ((AVLoadingIndicatorView) viewById.findViewById(R.id.avl_loading)).hide();

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public class PlayReceiver extends BroadcastReceiver {

        public static final String ACTION_NEW = "com.example.machao10.ACTION_NEW";
        public static final String ACTION_END = "com.example.machao10.ACTION_END";
        public static final String ACTION_UPDATE = "com.example.machao10.ACTION_UPDATE";

        public PlayReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_NEW:
                    break;
                case ACTION_END:
                    break;
                case ACTION_UPDATE:
                    long currentPosition = intent.getLongExtra("currentPosition", 0);
                    upDataSeek(currentPosition);
                    break;
            }
        }
    }

    public void upDataSeek(long currentPosition) {

    }

    private PlayReceiver playReceiver;

    private void registerMp3Receiver() {
        playReceiver = new PlayReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayReceiver.ACTION_NEW);
        filter.addAction(PlayReceiver.ACTION_END);
        filter.addAction(PlayReceiver.ACTION_UPDATE);
        registerReceiver(playReceiver, filter);
    }


}
