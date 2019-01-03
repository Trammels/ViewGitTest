package com.mls.baseProject.constant;


import com.mls.baseProject.application.AppContext;
import com.mls.baseProject.http.RetrofitManager;
import com.orhanobut.logger.Logger;

import static com.mls.baseProject.application.AppContext.getPreUtils;


/**
 * Created by CXX on 2015/11/13.
 */
public class UserPre {
    public static void saveUserId(String id) {
        getPreUtils().put(PrefConst.PRE_USER_ID, id);
    }

    public static String getUserId() {
        return getPreUtils().getString(PrefConst.PRE_USER_ID, "");
    }

    public static void saveUserName(String userName) {
        getPreUtils().put(PrefConst.PRE_USER_NAME, userName);
    }

    public static String getUserName() {
        return getPreUtils().getString(PrefConst.PRE_USER_NAME, "");
    }

    /**
     * 报存用户头像
     *
     * @param logo
     */
    public static void saveUserLogo(String logo) {
        getPreUtils().put(PrefConst.PRE_USER_LOGO, logo);
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public static String getUserLogo() {
        return getPreUtils().getString(PrefConst.PRE_USER_LOGO, "");
    }

    public static void saveCommpanyName(String name) {
        getPreUtils().put(PrefConst.PRECOMPANYNAME, name);
    }

    public static String getCommpanyName() {
        return getPreUtils().getString(PrefConst.PRECOMPANYNAME, "");
    }

    public static String getToken() {
        return getPreUtils().getString(PrefConst.PRE_USER_TOKEN, "");

    }

    public static void setToken(String token) {
        getPreUtils().put(PrefConst.PRE_USER_TOKEN, token);

    }

    /**
     * 设置积分
     *
     * @param point
     */
    public static void setPoint(String point) {
        getPreUtils().put(PrefConst.PRE_POINT, point);
    }

    /**
     * 获取积分
     *
     * @param
     */
    public static String getPoint() {
        return getPreUtils().getString(PrefConst.PRE_POINT, "0");
    }

    /**
     * 设置余额
     *
     * @param
     */
    public static void setBalance(String balance) {
        getPreUtils().put(PrefConst.PRE_BALANCE, balance);
    }

    /**
     * 获取余额
     *
     * @param
     */
    public static String getBalance() {
        return getPreUtils().getString(PrefConst.PRE_BALANCE, "0");
    }

    /**
     * 保存用户性别
     */
    public static void setGender(String gender) {
        getPreUtils().put(PrefConst.PRE_USER_GENDER, gender);
    }

    /**
     * 获取用户性别
     */
    public static String getGender() {
        return getPreUtils().getString(PrefConst.PRE_USER_GENDER, "male");
    }

    /**
     * 保存用户电话
     */
    public static void setPhone(String phoneCall) {
        getPreUtils().put(PrefConst.PRE_PHONE, phoneCall);
    }

    /**
     * 获取用户电话
     */
    public static String getPhone() {
        return getPreUtils().getString(PrefConst.PRE_PHONE, UserPre.getUserName());
    }


    /**
     * 保存用户昵称
     */

    public static void setNickName(String nickName) {
        getPreUtils().put(PrefConst.PRE_NICKNAME, nickName);
    }

    /**
     * 获取用户昵称
     */
    public static String getNickName() {
        return getPreUtils().getString(PrefConst.PRE_NICKNAME, "");
    }


    /**
     * 保存用户状态
     *
     * @param status 用户状态
     */
    public static void setUserStatus(String status) {
        getPreUtils().put(PrefConst.PRE_USER_STATUS, status);
    }

    /**
     * 获取用户状态
     *
     * @return
     */
    public static String getUserStatus() {
        return getPreUtils().getString(PrefConst.PRE_USER_STATUS, "");
    }


    public static void setCarIsAuth(boolean isAuth) {
        getPreUtils().put(PrefConst.PRE_CAR_AUTH, isAuth);
    }

    public static boolean getCarIsAuth() {
        return getPreUtils().getBoolean(PrefConst.PRE_CAR_AUTH, false);
    }

    /**
     * 保存会员认证的实体类
     *
     * @param auth
     */
    public static void saveAuthBean(String auth) {
        getPreUtils().put(PrefConst.PRE_USER_AUTH, auth);
    }

    /**
     * 获取会员认证的实体类
     *
     * @return
     */
    public static String getAuthBean() {
        return getPreUtils().getString(PrefConst.PRE_USER_AUTH, "");
    }

    /**
     * 保存银行卡
     *
     * @param blance
     */
    public static void saveBlance(String blance) {
        getPreUtils().put(PrefConst.PRE_BLANCE, blance);
    }

    /**
     * 获取银行卡
     *
     * @return
     */
    public static String getBlance() {
        return getPreUtils().getString(PrefConst.PRE_BLANCE, "");
    }

    /**
     * 保存无账户查询的手机
     *
     * @param userName
     */
    public static void saveNoUser(String userName) {
        getPreUtils().put(PrefConst.PRE_USER_NO, userName);
    }


    /**
     * 获取无账户查询的手机
     *
     * @return
     */
    public static String getNoUser() {
        return getPreUtils().getString(PrefConst.PRE_USER_NO, "");
    }

    /**
     * 保存Host
     *
     * @param host
     */
    public static void saveHost(String host) {
        getPreUtils().put(PrefConst.PRE_HOST,  host);
    }

    public static String getHost() {
        return getPreUtils().getString(PrefConst.PRE_HOST, "http://112.25.235.194:29133/");
//
        //正式环境
//        return getPreUtils().getString(PrefConst.PRE_HOST, "https://api.sinorelic.com");
    }

    /**
     * 保存纬度
     *
     * @param mLatitude
     */
    public static void saveLat(double mLatitude) {
        getPreUtils().put(PrefConst.PRE_LAT, mLatitude + "");
    }

    public static String getLat() {
        return getPreUtils().getString(PrefConst.PRE_LAT, "");

    }

    /**
     * 保存纬度
     *
     * @param mLatitude
     */
    public static void saveLong(double mLatitude) {
        getPreUtils().put(PrefConst.PRE_LONG, mLatitude + "");
    }

    public static String getLong() {
        return getPreUtils().getString(PrefConst.PRE_LONG, "");

    }

    /**
     * 保存用户词典
     *
     * @param s
     */
    public static void saveUserWord(String s) {
        getPreUtils().put(PrefConst.PRE_USER_WORD, s);
    }

    /**
     * 获取用户词典
     *
     * @return
     */
    public static String getUserWord() {
        return getPreUtils().getString(PrefConst.PRE_USER_WORD, "");
    }

    public static void saveUserWordMap(String s) {
        getPreUtils().put(PrefConst.PRE_USER_WORD_MAP, s);
    }

    public static String getUserWordMap() {
        return getPreUtils().getString(PrefConst.PRE_USER_WORD_MAP, "");
    }

    /**
     * 保存用户收藏站点状态
     */
    public static void setIsCollection(Boolean b) {
        getPreUtils().put(PrefConst.PRE_CURRENT_ISCOLLECTION, b);
    }

    public static void getIsCollection() {
        getPreUtils().put(PrefConst.PRE_CURRENT_ISCOLLECTION, "");
    }

    /**
     * 保存屏幕分辨率宽带
     *
     * @param width
     */
    public static void saveScreenWidth(int width) {
        getPreUtils().put(PrefConst.PRE_SCREEN_WIDTH, width);
    }

    /**
     * 获取屏幕分辨率宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return getPreUtils().getInt(PrefConst.PRE_SCREEN_WIDTH, 720);
    }


    /**
     * 保存屏幕分辨率高度
     *
     * @param height
     */
    public static void saveScreenHeight(int height) {
        getPreUtils().put(PrefConst.PRE_SCREEN_HEIGHT, height);
    }

    /**
     * 获取屏幕分辨率高度
     */
    public static int getScreenHeight() {
        return getPreUtils().getInt(PrefConst.PRE_SCREEN_HEIGHT, 1280);
    }

    public static void logout() {
        RetrofitManager.setmRetrofit(null);
        UserPre.saveUserId("");
        UserPre.saveUserName("");
        UserPre.setToken("");
        UserPre.saveUserLogo("");
        UserPre.setNickName("");
        UserPre.setPhone("");
        UserPre.setUserStatus("");
        UserPre.setIsVip(false);
        UserPre.setEnglishName("");
    }

    /**
     * 报存状态栏的高度
     *
     * @param statusBarHeight
     */
    public static void saveStatusBarHeight(int statusBarHeight) {
        getPreUtils().put(PrefConst.PRE_STATUS_BAR_HEIGHT, statusBarHeight);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        return getPreUtils().getInt(PrefConst.PRE_STATUS_BAR_HEIGHT, 50);
    }


    /**
     * 保存搜索记录
     *
     * @param searchContent
     */
    public static void saveSearchHistory(String searchContent) {
        getPreUtils().put(PrefConst.PRE_SEARCH_CONTENT, searchContent);
    }


    /**
     * 获取搜索记录
     *
     * @return
     */
    public static String getSearchHistory() {
        return getPreUtils().getString(PrefConst.PRE_SEARCH_CONTENT, "");
    }


    /**
     * 保存是否是第一次进入
     *
     * @param isOne
     */
    public static void saveIsOne(boolean isOne) {
        getPreUtils().put(PrefConst.PRE_IS_ONE, isOne);
    }

    /**
     * 获取是否是第一次进入
     *
     * @return
     */
    public static boolean getIsOne() {
        return getPreUtils().getBoolean(PrefConst.PRE_IS_ONE, true);
    }


    /**
     * 保存当前的版本号
     *
     * @param code
     */
    public static void saveCurrentCode(int code) {
        getPreUtils().put(PrefConst.PRE_CURRENT_VERSION_CODE, code);
    }

    /**
     * 获取当前的版本
     *
     * @return
     */
    public static int getCurrentCode() {
        return getPreUtils().getInt(PrefConst.PRE_CURRENT_VERSION_CODE, 1);
    }


    public static void setCheckInDate(long lastCheckInDate) {
        getPreUtils().put(PrefConst.PRE_IN_CHECK_DATE, lastCheckInDate);
    }

    public static Long getCheckInDate() {
        return getPreUtils().getLong(PrefConst.PRE_IN_CHECK_DATE, 0);
    }

    public static void setCheckInCount(String count) {
        getPreUtils().put(PrefConst.PRE_CHECK_IN_COUNT, count);
    }

    public static String getCheckInCount() {
        return getPreUtils().getString(PrefConst.PRE_CHECK_IN_COUNT, "0");
    }


    /**
     * 设置单句播放模式
     *
     * @param mode
     */
    public static void setSentenceMode(int mode) {
        getPreUtils().put(PrefConst.PRE_SENTENCE_MODE, mode);
    }

    /**
     * 获取单句播放模式
     *
     * @return
     */
    public static int getSentenceMode() {
        return getPreUtils().getInt(PrefConst.PRE_SENTENCE_MODE, 3);
    }

    public static void setPlaySpeed(String speed) {
        getPreUtils().put(PrefConst.PRE_SPEED, speed);
    }

    public static String getPlaySpeed() {
        return getPreUtils().getString(PrefConst.PRE_SPEED, "1");
    }

    public static void setStopMode(int mode) {
        getPreUtils().put(PrefConst.PRE_STOP_MODE, mode);
    }

    public static int getStopMode() {
        return getPreUtils().getInt(PrefConst.PRE_STOP_MODE, 3);
    }

    public static void setLoopMode(boolean isLoop) {
        getPreUtils().put(PrefConst.PRE_LOOP, isLoop);
    }

    public static boolean getLoopMode() {
        return getPreUtils().getBoolean(PrefConst.PRE_LOOP, false);
    }

    public static void setPlayTitle(String title) {
        Logger.d("保存播放名：" + title);
        getPreUtils().put(PrefConst.PLAY_TITLE, title);
    }

    public static String getPlayTitle() {
        Logger.d("获取：" + getPreUtils().getString(PrefConst.PLAY_TITLE, ""));
        return getPreUtils().getString(PrefConst.PLAY_TITLE, "");
    }

    public static void setEnglishName(String name) {
        getPreUtils().put(PrefConst.PRE_ENGLISH, name);
    }

    public static String getEnglishName() {
        return getPreUtils().getString(PrefConst.PRE_ENGLISH, "");
    }

    public static void setIsVip(boolean isVip) {
        getPreUtils().put(PrefConst.PRE_IS_VIP, isVip);
    }

    public static boolean getIsVip() {
        return AppContext.getPreUtils().getBoolean(PrefConst.PRE_IS_VIP, false);
    }

    public static void setIsFirst(boolean isFirst) {
        getPreUtils().put(PrefConst.PRE_ISFIRST, isFirst);
    }

    public static boolean getIsFirst() {
        return getPreUtils().getBoolean(PrefConst.PRE_ISFIRST, true);
    }

    /**
     * 联系我们
     *
     * @param url
     */
    public static void setContactUs(String url) {
        getPreUtils().put(PrefConst.CONTACTUS, url);
    }

    public static String getContactUs() {
        return getPreUtils().getString(PrefConst.CONTACTUS, "");
    }

    /**
     * 注册协议
     *
     * @param url
     */
    public static void setRegisterAgreement(String url) {
        getPreUtils().put(PrefConst.REGISTERAGREEMENT, url);
    }

    public static String getRegisterAgreementet() {
        return getPreUtils().getString(PrefConst.REGISTERAGREEMENT, "");
    }

    /**
     * 邀请地址
     *
     * @param url
     */
    public static void setInviteUrl(String url) {
        getPreUtils().put(PrefConst.INVITEURL, url);
    }

    public static String getInviteUrl() {
        return getPreUtils().getString(PrefConst.INVITEURL, "");
    }

    /**
     * 加入我们
     *
     * @param url
     */
    public static void setJoinUsUrl(String url) {
        getPreUtils().put(PrefConst.JOINUS, url);
    }

    public static String getJoinUsUrl() {
        return getPreUtils().getString(PrefConst.JOINUS, "");
    }

    /**
     * 分享地址
     *
     * @param url
     */
    public static void setShareUrl(String url) {
        getPreUtils().put(PrefConst.SHAREURL, url);
    }

    public static String getShareUrl() {
        return getPreUtils().getString(PrefConst.SHAREURL, "");
    }

    public static void setHowToMoney(String url) {
        getPreUtils().put(PrefConst.HOWTOMAKEMONEY, url);
    }

    public static String getHowToMoney() {
        return getPreUtils().getString(PrefConst.HOWTOMAKEMONEY, "");
    }

    public static void setHowToDown(String url) {
        getPreUtils().put(PrefConst.HOWTODOWNLOAD, url);
    }

    public static String getHowToDown() {
        return getPreUtils().getString(PrefConst.HOWTODOWNLOAD, "");
    }
}
