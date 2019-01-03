package gefei.com.viewdemo.layout;

import android.os.Bundle;

import com.mls.baseProject.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import gefei.com.viewdemo.R;

public class UIFlexBox extends BaseActivity {


    @Override
    protected void initView() {
        setContentView(R.layout.ui_flex_box);
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        startActivity(this,UIFlexBoxRecycleView.class);
    }
}
