package gefei.com.viewdemo.uianim;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.mls.baseProject.ui.BaseActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class UIGif extends BaseActivity {

    boolean isCancle;
    GifImageView mIvGif;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_uigif);

    }

    @Override
    protected void initData(Bundle bundle) {
        mIvGif = (GifImageView) findViewById(R.id.iv_gif1);
        final GifDrawable gifDrawable = (GifDrawable) mIvGif.getDrawable();
        gifDrawable.stop();
        gifDrawable.seekTo(1);
//        gifDrawable.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIvGif.setImageResource(R.drawable.anim);
//                gifDrawable.start();
            }
        }, 5000);
//        setGif(R.drawable.zongjueji_anim);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                upload();
//            }
//        }).start();
        startCountDownTime(1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(timer!=null){
                    timer.cancel();
                }
                isCancle = true;

                startCountDownTime(5);
            }
        }, 10000);

    }

    private CountDownTimer timer;

    private void startCountDownTime(final long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 需要注意的是：如果更新UI，界面消失的时候需要cancel，否则会异常
         * 有onTick，onFinsh、cancel和start方法
         */
        timer = new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
            }

            @Override
            public void onFinish() {
                if(!isCancle){
                    Logger.i("重连了webSocket");
                    startCountDownTime(2);
                }else{
                    Logger.i("重连了webSocketCancel");
                }

            }
        };
        timer.start();// 开始计时
    }

    private void upload() {
        String imgPath = Environment.getExternalStorageDirectory() + "/test.png";
//        String imgPath="/storage/emulated/0/Pictures/Picture_01_Shark.jpg";
        File file = new File(imgPath);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        String imageBytes = imageToBase64(imgPath);
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <SaveFile xmlns=\"http://tempuri.org/\">\n" +
                        "      <name>string</name>\n" +
                        "      <relateId>string</relateId>\n" +
                        "      <code>string</code>\n" +
                        "      <version>string</version>\n" +
                        "      <bytes>" + imageBytes + "</bytes>\n" +
                        "      <fileSize>" + file.getTotalSpace() + "</fileSize>\n" +
                        "      <src>string</src>\n" +
                        "    </SaveFile>\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>";
            // 创建url资源
            URL url = new URL("https://crmoem.jsti.com:8029/FileStore/Services/OuterService.asmx");
            //把XML的数据转成字符串
            String str = xml;
            byte[] bb = str.getBytes();
            //请求地址
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);//设置超时的时间
            conn.setDoInput(true);
            conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(bb.length));
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn
                        .getOutputStream());
            out.writeBytes(str); //写入请求的字符串
            out.flush();
            out.close();
            //请求返回的状态
            System.out.println(conn.getResponseCode());
            System.out.println(conn.getResponseMessage());

            if (conn.getResponseCode() == 200) {
                System.out.println("yes++");
                //得到响应流
                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String reponse = sb.toString();
                System.out.println(".............返回结果开始.............");
                System.out.println(reponse);
                System.out.println(".............返回结果结束.............");

                String strSave = "";
                String strNext = "";
                XmlPullParser parser = Xml.newPullParser();//获取pull解析器
                parser.setInput(inputStream, "utf-8");//设置输入流的编码方式
                int eventType = parser.getEventType();//得到第一个事件类型
                while (eventType != XmlPullParser.END_DOCUMENT) {//直到文档结束一直循环处理
                    if (eventType == XmlPullParser.START_DOCUMENT) {//文档开始
                    } else if (eventType == XmlPullParser.START_TAG) {//标签开始
                        String tagName = parser.getName();//获取标签名称
                        if (tagName != null) {
                            if (tagName.equals("SaveFileResponse")) {
                                strSave = parser.getAttributeValue(0);
                            } else if (tagName.equals("SaveFileResult")) {
                                strNext = parser.nextText();
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {//标签结束
                        String tagName = parser.getName();
                    }

                    eventType = parser.next();


                    Log.e("msg", "eventType=" + eventType + "strSave" + strSave + "strNext" + strNext);

                }


            } else {
                System.out.println("no++");
            }

        } catch (Exception e) {

        }

    }


    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public void setGif(int url) {
        Glide.with(this)
                    .load(url)
//                    .load("http://img.zcool.cn/community/017dad58c26484a801219c773df744.gif")
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mIvGif);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
