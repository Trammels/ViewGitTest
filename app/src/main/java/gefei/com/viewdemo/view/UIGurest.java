package gefei.com.viewdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mls.baseProject.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;
import gefei.com.viewdemo.view.lock.LockPatternView;

public class UIGurest extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_uigurest);
           ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle bundle) {

    }
}
