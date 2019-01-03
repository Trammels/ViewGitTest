package com.mls.baseProject.event;

/**
 * 公共通知
 * Created by CXX on 2016/5/30.
 */
public class NotifyEvent extends BaseEvent {
    /**
     * 登录
     */
    public static final int LOGIN = 0;  //登录
    /**
     * 退出登录
     */
    public static final int LOGOUT = 1; //退出登录
    /**
     * 选择检测项
     */
    public static final int CHOICE_DETECTION = 2;  //搜索结果
    /**
     * 网络错误
     */
    public static final int NET_ERROR = 3;  //网络错误
    /**
     * 用户登陆失效
     */
    public static final int INVALID_TOKEN_SURE = 4;  //用户登陆失效确定

    public static final int INVALID_TOKEN_CANCLE = 5;  //用户登陆失效取消

    /**
     * SYNC同步成功
     */
    public static final int SYNC_SUCCESS = 5;
    /**
     * 添加分类
     */
    public static final int ADD_CLASSIFY = 6;

    /**
     * 倒计时结束
     */
    public static final int TIME_END = 7;
    /**
     * 刷新下载列表
     */
    public static final int REFRESH_DOWN = 8;

    /**
     * 更新用户信息
     */
    public static final int UPDATA_USER_INFO = 9;

    /**
     * 打开购物车
     */
    public static final int OPEN_CART = 10;

    /**
     * 主页滚动到底部
     */
    public static final int SCOLLER_BOTTOM = 10;

    /**
     * 主页刷新
     */
    public static final int HOME_REFRESH = 11;

    /**
     * 主页价格点击
     */
    public static final int CLICK_PRICE = 12;
    /**
     * 刷新用户信息
     */
    public static final int START_USER_INFO = 13;
    /**
     * 微信登录成功
     */
    public static final int WX_LOGIN = 14;

    /**
     * 刷新下载列表进度
     */
    public static final int REFRESH_DOWN_PB = 15;
    /**
     * 视频播放
     */
    public static final int PLAY = 16;

    /**
     * 视频暂停
     */
    public static final int PAUSE = 17;
    /**
     * 视频暂停
     */
    public static final int PAUSE_PLAY = 18;
    /**
     * 视频进度
     */
    public static final int SEEK = 19;
    /**
     * 当前视频进度
     */
    public static final int CURRENT_SEEK = 20;
    /**
     * 回复
     */
    public static final int RESUME = 21;

    /**
     * 句子模式
     */
    public static final int SENTENCE_MODE = 22;
    /**
     * 句子模式播放
     */
    public static final int SENTENCE_PLAY = 23;

    /**
     * 停止播放服务
     */
    public static final int STOP_PLAY_SERVER = 24;
    /**
     * 开启会员
     */
    public static final int START_VIP = 25;

    /**
     * 获取单词
     */
    public static final int GET_WORD = 26;

    /**
     * 刷新字幕
     */
    public static final int REFRESH_SUBTITLE = 27;
    /**
     * 双击
     */
    public static final int DOUBLE_CLICK = 28;
    /**
     * POPUPWINDOW消失
     */
    public static final int POP_DISSMISS = 29;
    /**
     * 本地刷新单词
     */
    public static final int LOCAL_REFRESH_WORD = 30;
    /**
     * 播放器准备完成
     */
    public static final int PREPARE_COMPLETE = 31;
    /**
     * 点击下载状态
     */
    public static final int CLICK_DOWN_STATE = 32;
    /**
     * 微信支付成功
     */
    public static final int WX_PAY_SUCCESS = 33;
    /**
     * 下载
     */
    public static final int START_DOWN = 34;

    /**
     * 下载打开
     */
    public static final int START_DOWN_OPEN = 35;

    /**
     * 下载暂停
     */
    public static final int START_DOWN_PANUSE = 36;


    /**
     * 暂停所有
     */
    public static final int START_DOWN_OPEN_ALL = 37;

    /**
     * 开始所有
     */
    public static final int START_DOWN_PANUSE_ALL = 38;
    /**
     * 开始所有
     */
    public static final int REFRESHDOWNTOP = 39;
    private int value;
    private Object object;
    private Object objectMsg;
    private Object object1;
    private String name;
    private Object params1;
    private Object params2;
    private Object params3;
    private Object params4;
    private Object params5;

    public Object getParams5() {
        return params5;
    }

    public void setParams5(Object params5) {
        this.params5 = params5;
    }

    public Object getParams4() {
        return params4;
    }

    public void setParams4(Object params4) {
        this.params4 = params4;
    }

    public Object getParams1() {
        return params1;
    }

    public void setParams1(Object params1) {
        this.params1 = params1;
    }

    public Object getParams2() {
        return params2;
    }

    public void setParams2(Object params2) {
        this.params2 = params2;
    }

    public Object getParams3() {
        return params3;
    }

    public void setParams3(Object params3) {
        this.params3 = params3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObjectMsg() {
        return objectMsg;
    }

    public void setObjectMsg(Object objectMsg) {
        this.objectMsg = objectMsg;
    }

    public Object getObject1() {
        return object1;
    }

    public void setObject1(Object object1) {
        this.object1 = object1;
    }
}
