package gefei.com.viewdemo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mls.baseProject.util.DialogUtil;
import com.orhanobut.logger.Logger;

import gefei.com.viewdemo.R;
import io.vov.vitamio.utils.Log;

public class UIOpenHtml extends AppCompatActivity {
    WebView webView;
    private String url;
    /**
     * 处理加载完成，error等问题
     */
    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView wv, String url) {
            if (url == null) return false;
            try {
                if (url.startsWith("weixin://") || url.startsWith("alipays://") ||
                            url.startsWith("mailto://") || url.startsWith("tel://")
                    //其他自定义的scheme
                            ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return false;
            }
            //处理http和https开头的url
            wv.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView wv, WebResourceRequest request) {
            wv.loadUrl(url);
            return true;
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
//            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            DialogUtil.dissMissLoadingDialog();
            webView.loadUrl("javascript:setPatrolPoint()");

        }
    };

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiopen_html);
        webView = (WebView) findViewById(R.id.webView);
        url = "http://10.11.0.141:60001/GWMap/test.html";
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webView.loadUrl(url);
        setWebView(url);
        webView.addJavascriptInterface(UIOpenHtml.this, "android");

        findViewById(R.id.btn_webView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("1111", "111111111111");
                webView.loadUrl("javascript:setPatrolPoint()");

            }
        });

    }

    /**
     * 加载H5
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void setWebView(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                        WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        //加载H5参数
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 启用js
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient());


        webView.setWebViewClient(webViewClient);
        webView.loadUrl(content);
        //js调用
        webView.addJavascriptInterface(new JavaScriptinterface(this),
                    "android");
//        webView.loadUrl("javascript:setPatrolPoint()");
    }

    public class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context c) {
            context = c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        //文章id
        @JavascriptInterface
        public void setPatrolPoint() {


        }

        //图片id
        @JavascriptInterface
        public void showPhoto(String photoId) {
            Logger.e("" + "showPhoto" + "、" + photoId);
//
        }

        //足迹id
        @JavascriptInterface
        public void showFoot(String msg) {
            Logger.e("" + "》》》》》" + "、" + msg);


        }

        //文保点id
        @JavascriptInterface
        public void showCorrelationRelicPoint(String msg, String name) {
            Logger.e("" + "》》》》》" + "、" + msg);


        }

    }


}
