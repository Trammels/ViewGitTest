package gefei.com.viewdemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mls.baseProject.ui.BaseActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;

/**
 * Activity - 文字转语音
 */
public class UITTSActivity extends BaseActivity implements TextToSpeech.OnInitListener {


    @BindView(R.id.btn_tts)
    Button btnTts;
    private TextToSpeech tts;

    @Override
    protected void initView() {
        setContentView(R.layout.ui_tts);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle bundle) {
        tts = new TextToSpeech(UITTSActivity.this, this);

        btnTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
            }
        });
    }

    @SuppressLint("NewApi")
    public void play(View view){
        String str = btnTts.getText().toString().trim();
        if (!TextUtils.isEmpty(str)){
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            tts.setPitch(1.0f);
            // 设置语速
            tts.setSpeechRate(1.0f);
            //播放语音
            tts.speak(str, TextToSpeech.QUEUE_ADD, null,null);
        }
    }

    @Override
    public void onInit(int status) {
        //判断tts回调是否成功
        if (status == TextToSpeech.SUCCESS) {
            int result1 = tts.setLanguage(Locale.US);
            int result2 = tts.setLanguage(Locale.CHINESE);
            if (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED
                        || result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
