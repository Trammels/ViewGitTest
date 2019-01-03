package gefei.com.viewdemo.layout;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.mls.baseProject.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;
import gefei.com.viewdemo.adapter.UIFlexBoxRecycleViewAdapter;
import gefei.com.viewdemo.entity.UIFlexBoxRecycleViewResponse;

public class UIFlexBoxRecycleView extends BaseActivity {


    @BindView(R.id.test_recyclerView)
    RecyclerView mTestRecyclerView;
    private UIFlexBoxRecycleViewAdapter adapter;
    private List<UIFlexBoxRecycleViewResponse> mDatas;
    @Override
    protected void initView() {
        setContentView(R.layout.ui_flex_box_recycle_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle bundle) {
        mDatas = new ArrayList<>();
        mDatas.add(new UIFlexBoxRecycleViewResponse("第一次来，很好"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("再次路过"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("最好的季节里，遇见你"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("对不起，不开放"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("这辈子该来一次的"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("走了许久遇见你，真好"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("来此一拜"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("早点来不要门票"));
        mDatas.add(new UIFlexBoxRecycleViewResponse("第一次来，很好"));

        adapter = new UIFlexBoxRecycleViewAdapter(mDatas);
//        mTestRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        FlexboxLayoutManager manager = new FlexboxLayoutManager();
        //设置主轴排列方式
        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);
        mTestRecyclerView.setLayoutManager(manager);
        mTestRecyclerView.setAdapter(adapter);
    }

}
