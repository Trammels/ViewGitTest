package com.mls.baseProject.http;


import android.util.Base64;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.mls.baseProject.constant.UserPre;
import com.mls.baseProject.util.StringUtils;
import com.mls.baseProject.util.UIUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.OnErrorFailedException;
import rx.internal.operators.OnSubscribeTimerOnce;
import rx.schedulers.Schedulers;

/**
 * retrofit的配置类
 * Created by CXX on 2016/7/27.
 */
public class RetrofitManager<T> {
    private static Retrofit mRetrofit;
    public static final String API_URL = UserPre.getHost();
    public static OkHttpClient okHttpClient;
    private static SSLContext sslContext;
    private static final String CER_NAME = "";   //https签名证书name

    public RetrofitManager() {
    }

    public static Retrofit retrofit() {

        if (mRetrofit == null) {
            initHttpClient();
            mRetrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
        }
        return mRetrofit;
    }

    private static void initHttpClient() {
        if (StringUtils.isEmpty(CER_NAME)) {
            //忽略所有证书
            overlockCard();
        } else {
            //选择证书
            try {
                setCard(UIUtils.getContext().getAssets().open(CER_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)

                    .sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier((hostname, session) -> true)
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }

                    })
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader("Authorization", UserPre.getToken()).
                                    addHeader("FrontActiveEntId", "3")
                                    .addHeader("accept-language","zh-CN")
                                    .build();
                        return chain.proceed(request);
                    })
                    .build();

    }


    /**
     * 读取https证书
     *
     * @param certificate
     */
    public static void setCard(InputStream certificate) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            String certificateAlias = Integer.toString(0);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
            sslContext = SSLContext.getInstance("TLS");
            final TrustManagerFactory trustManagerFactory =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init
                        (
                                    null,
                                    trustManagerFactory.getTrustManagers(),
                                    new SecureRandom()
                        );
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


    /**
     * 忽略所有https证书
     */
    private static void overlockCard() {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        }};
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                        new SecureRandom());
        } catch (Exception e) {
        }

    }

    public static Observable schedules(Observable observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static void setmRetrofit(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public static String base64(Object object) {
        String encodedString = Base64.encodeToString(new Gson().toJson(object).getBytes(), Base64.DEFAULT);
        return StringUtils.replaceBlank(encodedString);
    }
}
