package com.mls.baseProject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gefei on 2018/3/22.
 * 保存图片到本地
 */

public class saveImageToSdCardUtil {
    public static String IMAGE_NAME = "iv_share_";
    public static int i = 0;

    //根据网络图片url路径保存到本地
    public static final File saveImageToSdCard(Context context, String image) {
        boolean success = false;
        File file = null;
        try {
            file = createStableImageFile(context);

            Bitmap bitmap = null;
            URL url = new URL(image);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            FileOutputStream outStream;

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            return file;
        } else {
            return null;
        }
    }

    //创建本地保存路径
    public static File createStableImageFile(Context context) throws IOException {
        i++;
        String imageFileName = IMAGE_NAME + i + ".jpg";
        File storageDir = context.getExternalCacheDir();
        File image = new File(storageDir, imageFileName);
        return image;
    }
}
