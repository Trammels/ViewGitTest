package gefei.com.viewdemo.progress;

import android.os.Bundle;

import com.mls.baseProject.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;
import gefei.com.viewdemo.util.XCRoundProgressBar;

public class UIProgress extends BaseActivity {


    @BindView(R.id.progress)
    XCRoundProgressBar mProgress;
    @BindView(R.id.progress2)
    XCRoundProgressBar mProgress2;
    @BindView(R.id.progress3)
    XCRoundProgressBar mProgress3;

    @Override
    protected void initView() {
        setContentView(R.layout.ui_progress);
        ButterKnife.bind(this);
        initTitle("仪表盘");
    }

    @Override
    protected void initData(Bundle bundle) {
        mProgress.setStartAngle(-195);
        mProgress.setEndAngle(215);
        mProgress.setProgress(10);
        mProgress.setMax(100);
        mProgress2.setStartAngle(-270);
        mProgress2.setEndAngle(180);
        mProgress2.setProgress(30);
        mProgress2.setMax(100);
        mProgress3.setStartAngle(90);
        mProgress3.setEndAngle(-180);
//        mProgress3.setStartAngle(270);
//        mProgress3.setEndAngle(180);
        mProgress3.setProgress(80);
        mProgress3.setMax(100);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
