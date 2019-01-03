package gefei.com.viewdemo.material;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mls.baseProject.ui.BaseActivity;

import butterknife.ButterKnife;
import gefei.com.viewdemo.R;

/**
 * editText输入文字后上移
 */
public class TextInputLayout extends BaseActivity {


    @Override
    protected void initView() {
        setContentView(R.layout.activity_text_input_layout);
        ButterKnife.bind(this);
        initTitle("TextInputLayout");
    }

    @Override
    protected void initData(Bundle bundle) {

    }
}
