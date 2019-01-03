package com.mls.baseProject.constant;

import android.os.Environment;

import java.io.File;

/**
 * 公共数据类
 * Created by CXX on 2016/5/30.
 */
public class Common {
    public static String WEB_SHOW_URL = "http://192.168.19.58:8080/";
    public static String SUCCESS = "success";
    public static String NO_DATA = "noData";
    public static String ERROR = "error";
    public static String INVALID_TOKEN = "invalidPrincipal";  //用户登陆失效
    public static String API_VERSION = "/api/client/v1";
    public static int PAGE_SIZE = 10;  //每页显示的数据个数
    public static int AROUND_DISTANCE = 50 * 1000;   //附近显示的充电站位置距离
    public static int ERROR_NET = 1;  //网络错误
    public static int NO_DATA_ERRO = 2;  //没有数据
    public static int APP_ERROR = 3;   //错误
    public static int APP_TIME_OUT = 3;   //错误
    public static String DOWN_PATH = Environment.getExternalStorageDirectory() + File.separator + "ycyw" + File.separator + "download";
}
