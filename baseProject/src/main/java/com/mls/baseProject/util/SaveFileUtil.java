package com.mls.baseProject.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by gefei on 2018/4/2.
 * 保存文件到本地
 */

public class SaveFileUtil {

    public static Boolean setToken(String token) throws IOException {

        String path = "./mnt/sdcard/ucabCrash/";
        String fileName = "atoken.txt";

        File dirFile = new File(path);
        File file = new File(path + fileName);

        if (!dirFile.exists()) {
            dirFile.mkdirs();
            try {
                file.createNewFile();
            } catch (Exception e) {
                Log.e("calm", "------创建异常---");
                return false;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.getDefault());
        long timestamp = System.currentTimeMillis();
        String time = sdf.format(new Date(timestamp));
        token = time + token + '\n';
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(path + fileName, true)));
            out.write(token);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;

    }


    public static void saveToSDCard(String filename, String content) throws Exception {
        /*
         * 保存文件到sd卡，sd卡用于保存大文件（视频，音乐，文档等）
         * 获取sd卡路径Environment.getExternalStorageDirectory()
         * android版本不同，sd卡的路径也不相同，所以这里不能写绝对路径
         * */
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream outStream = new FileOutputStream(file);
        if (!file.exists()) {
            file.mkdirs();
            try {
                file.createNewFile();
            } catch (Exception e) {
                Log.e("calm", "------创建异常---");
            }
        }
        /**
         *
         * 注意：这里创建文件输出流对象 就 不能使用context.openFileOutput(filename, Context.MODE_PRIVATE);这中方式创建了这种方式会直接在手机自带的内存中创建文件，
         在sd卡下创建文件要使用new FileOutputStream(file);
         */
        content = content +"/"+ com.mls.baseProject.util.TimeUtils.millis2String(System.currentTimeMillis());
        outStream.write(content.getBytes());
        outStream.close();
    }
}
