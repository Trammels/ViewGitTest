package gefei.com.viewdemo.material;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mls.baseProject.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gefei.com.viewdemo.R;

public class UIFloatingActionButton extends AppCompatActivity {

    @BindView(R.id.action_a)
    FloatingActionButton mActionA;
    @BindView(R.id.action_b)
    FloatingActionButton mActionB;
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu mMultipleActions;
    @BindView(R.id.button_remove)
    FloatingActionButton mButtonRemove;
    @BindView(R.id.button_gone)
    FloatingActionButton mButtonGone;
    @BindView(R.id.action_enable)
    FloatingActionButton mActionEnable;
    @BindView(R.id.multiple_actions_down)
    FloatingActionsMenu mMultipleActionsDown;
    private boolean isAdd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_action_button);
        ButterKnife.bind(this);
//        final View actionB = findViewById(R.id.action_b);
//        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
//        actionC.setTitle("Hide/Show Action above");
//        actionC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            }
//        });
//        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
//        menuMultipleActions.addButton(actionC);
//        final FloatingActionButton removeAction = (FloatingActionButton) findViewById(R.id.button_remove);
//        removeAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
//            }
//        });
//        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
//        actionA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                actionA.setTitle("Action A clicked");
//
//            }
//        });
//        findViewById(R.id.button_gone).setVisibility(View.GONE);
//        final FloatingActionButton actionEnable = (FloatingActionButton) findViewById(R.id.action_enable);
//        actionEnable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
//            }
//        });

    }

    @OnClick({R.id.action_a, R.id.action_b, R.id.button_remove, R.id.button_gone, R.id.action_enable})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_a:
                ToastUtil.show("action_a");
                break;
            case R.id.action_b:
                ToastUtil.show("action_b");
                break;
            case R.id.button_remove:
                ToastUtil.show("button_remove");
                break;
            case R.id.button_gone:
                ToastUtil.show("button_gone");
                break;
            case R.id.action_enable:
                ToastUtil.show("action_enable");
                break;

        }
    }
}
