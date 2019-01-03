package gefei.com.viewdemo.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mls.baseProject.ui.BaseActivity;
import com.mls.baseProject.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gefei.com.viewdemo.R;
import gefei.com.viewdemo.layout.UIFlexBox;
import gefei.com.viewdemo.material.UIMeterial;
import gefei.com.viewdemo.progress.UIProgress;
import gefei.com.viewdemo.rtmp.VitamioVideoViewActivity;
import gefei.com.viewdemo.ui.UIOpenHtml;
import gefei.com.viewdemo.ui.UITTSActivity;
import gefei.com.viewdemo.ui.UITwoLayout;
import gefei.com.viewdemo.uianim.UICarAnim;
import gefei.com.viewdemo.uianim.UIGif;
import gefei.com.viewdemo.uianim.UIPull;
import gefei.com.viewdemo.view.UIGurest;
import gefei.com.viewdemo.webview.UIWebView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.btn_car_anim)
    Button mBtnCarAnim;
    @BindView(R.id.btn_progress)
    Button mBtnProgress;
    @BindView(R.id.ll)
    LinearLayout ll;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTitle("动画首页");
    }

    @Override
    protected void initData(Bundle bundle) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(UIUtils.px2dip(800), UIUtils.px2dip(500)));
//        params.height = px2dip(50);
//        params.width = px2dip(200);
//        mBtnCarAnim.setLayoutParams(params);
        params.weight = UIUtils.getScreenWidth(this) / 2;

        Button button = new Button(this);
//        button.setWidth(UIUtils.getScreenWidth(this));
        button.setText("测试1");
        button.setLayoutParams(params);
        ll.addView(button);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @OnClick(R.id.btn_car_anim)
    public void onViewClicked() {

    }


    @OnClick({R.id.btn_car_anim, R.id.btn_progress, R.id.btn_liushi, R.id.btn_liushi2, R.id.btn_rtmp, R.id.btn_gif, R.id.btn_meterial,
                R.id.btn_webView, R.id.btn_gesture, R.id.btn_two_layout, R.id.btn_open_html, R.id.btn_tts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_car_anim:
                startActivity(this, UICarAnim.class);
                break;
            case R.id.btn_gif:
                startActivity(this, UIGif.class);
                break;
            case R.id.btn_progress:
                startActivity(this, UIProgress.class);
                break;
            case R.id.btn_liushi:
                startActivity(this, UIPull.class);
                break;
            case R.id.btn_liushi2:

                startActivity(this, UIFlexBox.class);
                break;

            case R.id.btn_rtmp:
                Intent intent = new Intent(MainActivity.this, VitamioVideoViewActivity.class);
                intent.putExtra("movieUrl", "rtmp://192.168.19.173:10935/hls/stream_1");
                startActivity(intent);
                break;
            case R.id.btn_meterial:
                startActivity(this, UIMeterial.class);
                break;
            case R.id.btn_webView:
                startActivity(this, UIWebView.class);
                break;
            case R.id.btn_gesture:
                startActivity(this, UIGurest.class);
                break;
            case R.id.btn_two_layout:
                startActivity(this, UITwoLayout.class);
                break;
            case R.id.btn_open_html:
                startActivity(this, UIOpenHtml.class);
                break;
            case R.id.btn_tts:
                startActivity(this, UITTSActivity.class);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
