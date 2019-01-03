package gefei.com.viewdemo.material;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.mls.baseProject.ui.BaseActivity;
import com.mls.baseProject.util.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gefei.com.viewdemo.R;
import jp.wasabeef.glide.transformations.internal.Utils;

/**
 * 替代Toast的控件，配合Snackbar可以右滑删除
 */
public class UISnackbar extends BaseActivity {


    @BindView(R.id.tv_hint)
    TextView mTvHint;
    @BindView(R.id.cl_container)
    CoordinatorLayout mClContainer;

    @Override
    protected void initView() {
        setContentView(R.layout.ui_snackbar);
        ButterKnife.bind(this);
        initTitle("Snackbar");
    }

    @Override
    protected void initData(Bundle bundle) {

    }


    @OnClick({R.id.btn_snackbar_simple, R.id.btn_snackbar_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_snackbar_simple:
                Snackbar.make(mClContainer, "把我往右滑动看看会发生什么事", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btn_snackbar_action:
                Snackbar.make(mClContainer, "这是一个可交互的提示条", Snackbar.LENGTH_SHORT);
//                Snackbar.make(mClContainer, "这是一个可交互的提示条", Snackbar.LENGTH_LONG)
//                            .setAction("点我", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    mTvHint.setText(TimeUtils.millis2Stringnosecond(System.currentTimeMillis())+" 您轻轻点了一下Snackbar");
//                                }
//                            }).setActionTextColor(Color.YELLOW).show();
                break;
        }
    }
}
