package com.mls.baseProject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.mls.baseProject.R;
import com.mls.baseProject.constant.Common;
import com.mls.baseProject.util.UIUtils;
import com.mls.baseProject.widget.PtrDefaultHeader;
import com.mls.baseProject.widget.PullToLoadMoreListView;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 所有Fragment的基类
 * 说明：
 * ==================================
 * addXxxxView()：表示添加界面为空时的布局
 * 、添加界面加载中的布局、添加网络错误时的布局
 * 使用说明：
 * 在子类中直接调如addEmptyView（）界面将会显示数据为空显示的布局
 * 注意：在子类的fragment中的布局文件中需要有FrameLayout为子节点的布局
 * 因为如果为线性布局添加的view不会在布局中显示并且id要为fl_main
 * 对网络错误的点击处理子类可以覆写 onNetErrorClick（）方法
 * 移除布局的方法都放在了网络处理层子类无需调用
 * ======================================
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener, InvokeListener, TakePhoto.TakeResultListener {

    protected View mView;
    protected int mViewId;
    private LinearLayout relArea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        onFragmentCreate(savedInstanceState);
    }

    protected abstract void onFragmentCreate(Bundle savedInstanceState);

    protected void setContentView(int resourceId) {
        mViewId = resourceId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = LayoutInflater.from(getActivity()).inflate(mViewId, null);
            ButterKnife.bind(this, mView);
        }
        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mView);
        initData(savedInstanceState);
    }

    /**
     * 当子类需要刷新时 直接调用此方法会生成对应的refresh如果需要上拉加载会生成loadMore方法
     * refresh和loadMore为两个空实现
     *
     * @param ptrMain
     * @param lvContent
     */
    public void addRefresh(PtrFrameLayout ptrMain, final ListView lvContent) {
        PtrDefaultHeader header = new PtrDefaultHeader(getActivity());
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

        if (lvContent != null && lvContent instanceof PullToLoadMoreListView) {
            ((PullToLoadMoreListView) lvContent).setOnLoadMoreListener(() -> loadMore());
        }

    }

    /**
     * 下拉刷新
     *
     * @param frame
     */
    public void refresh(PtrFrameLayout frame) {
        canRefresh = false;
    }


    /**
     * 上拉加载
     */
    public void loadMore() {

    }

    protected abstract void initView(View view);

    protected abstract void initData(Bundle savedInstanceState);

    protected View findViewById(int resourceId) {
        return mView.findViewById(resourceId);
    }

    protected ListView initTitle(String title) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setText(title);
        findViewById(R.id.txt_action_right).setVisibility(View.GONE);
        findViewById(R.id.img_action_left).setOnClickListener(this);
        return null;
    }

    protected void initTitle(String title, String content) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        TextView txtRight = (TextView) findViewById(R.id.txt_action_right);
        txtTitle.setText(title);
        txtRight.setText(content);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(this);
        txtTitle.setOnClickListener(this);
        findViewById(R.id.img_action_left).setOnClickListener(this);
    }

    protected void initTitle(String title, int rightImgId) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        ImageView ivLeft = (ImageView) findViewById(R.id.img_action_left);
        ImageView ivRight = (ImageView) findViewById(R.id.img_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(rightImgId);
        ivLeft.setVisibility(View.GONE);
        txtTitle.setText(title);
        findViewById(R.id.img_right).setOnClickListener(this);
    }

    protected void initTitle(String title, int leftImgId, int rightImgId) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        ImageView ivLeft = (ImageView) findViewById(R.id.img_action_left);
        ImageView ivRight = (ImageView) findViewById(R.id.img_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(rightImgId);
        ivLeft.setImageResource(leftImgId);
        txtTitle.setText(title);
        findViewById(R.id.img_action_left).setOnClickListener(this);
        findViewById(R.id.img_right).setOnClickListener(this);
    }

    protected void initTitle(String title, String right, boolean isShow) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        TextView txtRight = (TextView) findViewById(R.id.txt_action_right);
        txtTitle.setText(title);
        txtRight.setText(right);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(this);
        txtTitle.setOnClickListener(this);
        if (!isShow) {
            findViewById(R.id.img_action_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.img_action_left).setOnClickListener(this);
        }
    }

    protected void initTitle(int title, int content, boolean isShow) {
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

    protected void initTitle(int title, int right, int leftContent) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        TextView txtLeft = (TextView) findViewById(R.id.txt_action_left);
        TextView txtRight = (TextView) findViewById(R.id.txt_action_right);
        txtTitle.setText(title);
        txtRight.setText(right);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(this);
        txtTitle.setOnClickListener(this);
        findViewById(R.id.img_action_left).setVisibility(View.GONE);
        txtLeft.setVisibility(View.VISIBLE);
        txtLeft.setText(leftContent);
    }


    protected void initTitle(String title, boolean isShow) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        txtTitle.setText(title);
        txtTitle.setOnClickListener(this);
        if (!isShow) {
            findViewById(R.id.img_action_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.img_action_left).setVisibility(View.VISIBLE);
            findViewById(R.id.img_action_left).setOnClickListener(this);
        }
    }

    protected void initTitle(String title, boolean isShow, String right) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        txtTitle.setText(title);
        txtTitle.setOnClickListener(this);
        if (!isShow) {
            findViewById(R.id.img_action_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.img_action_left).setVisibility(View.VISIBLE);
            findViewById(R.id.img_action_left).setOnClickListener(this);
        }
        TextView txtRight = (TextView) findViewById(R.id.txt_action_right);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText(right);
    }

    protected void initTitle(int title, boolean isShow) {
        TextView txtTitle = (TextView) findViewById(R.id.txt_action_title);
        txtTitle.setOnClickListener(this);
        txtTitle.setText(title);
        txtTitle.setOnClickListener(this);
        if (!isShow) {
            findViewById(R.id.img_action_left).setVisibility(View.GONE);
        } else {
            findViewById(R.id.img_action_left).setVisibility(View.VISIBLE);
            findViewById(R.id.img_action_left).setOnClickListener(this);
        }
    }

    /**
     * 显示toast
     *
     * @param resId
     */
    public void showToast(int resId) {
        Toast.makeText(getActivity(), this.getResources().getText(resId), Toast.LENGTH_SHORT).show();

    }

    /**
     * 显示toast
     *
     * @param text
     */
    public void showToast(String text) {

        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

    }

    public void startActivity(Context fromClass, Class<?> toClass) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        fromClass.startActivity(intent);
    }

    public void startActivity(Context fromClass, Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        fromClass.startActivity(intent);
    }

    public void startActivityForResult(Context fromClass, Class<?> toClass, int requsetCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requsetCode);
    }

    public void startActivityForResult(Context fromClass, Class<?> toClass, int requsetCode) {
        startActivityForResult(fromClass, toClass, requsetCode, null);
    }


    @Override
    public void onClick(View v) {

    }

    protected void onRightActionPressed() {

    }

    public boolean canRefresh = false;


    View emptyView;

    /**
     * 添加界面为空的布局 根布局要为FrameLayout
     */
    public void addEmptyView() {
        ViewGroup contentView = (ViewGroup) mView.findViewById(R.id.fl_content);
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
        ViewGroup contentView = (ViewGroup) mView.findViewById(R.id.fl_content);
        if (emptyView != null)
            (contentView).removeView(emptyView);
        emptyView = null;
    }

    /**
     * 添加界面为空的布局 根布局要为FrameLayout
     */
    public void addEmptyView(int imgSrc, String content) {
        ViewGroup contentView = (ViewGroup) mView.findViewById(R.id.fl_content);
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
    public void addEmptyViewNo(int imgSrc, String content) {
        ViewGroup contentView = (ViewGroup) mView.findViewById(R.id.fl_content);
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
        ViewGroup contentView = (ViewGroup) mView.findViewById(R.id.fl_content);
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


    /**
     * 添加界面为空的布局 根布局要为FrameLayout
     */
    public void addEmptyViewOrder() {
        canRefresh = true;
        FrameLayout flMain = (FrameLayout) mView.findViewById(R.id.fl_main);
        if (emptyView == null) {
            emptyView = UIUtils.inflate(R.layout.view_empty);
            emptyView.findViewById(R.id.lin_main).setOnClickListener(v -> {

            });
            flMain.addView(emptyView);
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
        netErrorView = UIUtils.inflate(R.layout.view_net_error);
        FrameLayout flMain = (FrameLayout) mView.findViewById(R.id.fl_main);
        flMain.addView(netErrorView);
        LinearLayout linMain = (LinearLayout) netErrorView.findViewById(R.id.lin_net_main);
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
        if (netErrorView == null) {
            return;
        }
        FrameLayout flMain = (FrameLayout) mView.findViewById(R.id.fl_main);

        if (netErrorView != null) {
            canRefresh = true;
            flMain.removeView(netErrorView);
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
     * 添加错误布局界面 需要修改布局显示的图片和内容
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
     * 添加错误布局界面 需要修改布局显示的图片和内容
     */
    public void addErrorView(int errorCode, int imgSrc, String content, String content_sencond) {
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
    public void addErrorView(int errorCode, int pageSize, PullToLoadMoreListView lvContent, int imgSrc, String content, String content_sencond) {
        if (errorCode == Common.ERROR_NET) {
            addNetErrorView();
        } else if (errorCode == Common.NO_DATA_ERRO) {
            if (pageSize > 0) {
                lvContent.loadMoreComplete();
                lvContent.setHasMore(false);
            } else {
                addEmptyViewOrder();
            }
        } else {
            addEmptyViewOrder();
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

        View linLoading = mView.findViewById(R.id.lin_loading);
        lodingView = linLoading;
        if (linLoading == null) {

        } else {
            mView.findViewById(R.id.lin_loading).setVisibility(View.VISIBLE);
            ((AVLoadingIndicatorView) mView.findViewById(R.id.avl_loading)).show();
        }
    }

    /**
     * 移除加载中的布局
     */
    public void removeLodingView() {

        View linLoading = mView.findViewById(R.id.lin_loading);
        if (lodingView == null) {

        } else {
            lodingView.setVisibility(View.GONE);
            ((AVLoadingIndicatorView) mView.findViewById(R.id.avl_loading)).hide();

        }

    }

    private TakePhoto takePhoto;

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    private InvokeParam invokeParam;

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
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
}
