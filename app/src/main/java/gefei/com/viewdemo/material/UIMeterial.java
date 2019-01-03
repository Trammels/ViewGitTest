package gefei.com.viewdemo.material;

import android.os.Bundle;
import android.view.View;

import com.mls.baseProject.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import gefei.com.viewdemo.R;

public class UIMeterial extends BaseActivity {


    @Override
    protected void initView() {
        setContentView(R.layout.ui_meterial);
        ButterKnife.bind(this);
        initTitle("UIMeterial");
    }

    @Override
    protected void initData(Bundle bundle) {

    }


    @OnClick({R.id.TextInputLayout, R.id.floatButton,R.id.snackBar,R.id.coordinatorLayout,R.id.palette})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.TextInputLayout:
                startActivity(this, TextInputLayout.class);
                break;
            case R.id.floatButton:
                startActivity(this, UIFloatingActionButton.class);
                break;
            case R.id.snackBar:
                startActivity(this, UISnackbar.class);
                break;
            case R.id.coordinatorLayout:
                startActivity(this, UICoordinatorLayout.class);
                break;
            case R.id.palette:
//                startActivity(this, UIPalette.class);
                break;
        }
    }
}
