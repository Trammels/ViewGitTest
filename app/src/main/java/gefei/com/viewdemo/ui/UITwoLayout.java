package gefei.com.viewdemo.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mls.baseProject.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gefei.com.viewdemo.R;
import gefei.com.viewdemo.adapter.TwoLayoutAdapter;
import gefei.com.viewdemo.entity.TwoLayoutResponse;

/**
 * 双层布局，多层布局
 */

public class UITwoLayout extends BaseActivity {
    @BindView(R.id.rv_two_layout)
    RecyclerView mRvTwoLayout;
    private TwoLayoutAdapter mTwoLayoutAdapter;
    private List<TwoLayoutResponse> mList;
    private List<TwoLayoutResponse> mList1;
    private List<TwoLayoutResponse.TwoBean> mTwoList;
    private List<TwoLayoutResponse.TwoBean> mTwoList1;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_uitwo_layout);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle bundle) {
        mList = new ArrayList<>();
        mList1 = new ArrayList<>();
        mTwoList = new ArrayList<>();
        mTwoList1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList1.add(new TwoLayoutResponse("第" + (i + 1) + "层", null));
            mTwoList1.add(new TwoLayoutResponse.TwoBean("子类" + (i+1) + ""));
            mTwoList.add(new TwoLayoutResponse.TwoBean("子类" + i + ""));

        }

        for (int i = 0; i < mList1.size(); i++) {
            TwoLayoutResponse mTwoLayoutResponse = new TwoLayoutResponse();
            mTwoLayoutResponse.setName(mList1.get(i).getName());
            mTwoLayoutResponse.setItemType(TwoLayoutResponse.HEADER);
            mList.add(mTwoLayoutResponse);
//            mTwoList.get(i).setName(mList1.get(i).getName());
            for (int j = 0; j < mTwoList1.size(); j++) {
                TwoLayoutResponse mTwoLayoutResponse2 = new TwoLayoutResponse();
//                mTwoList.get(j).setName(mTwoList1.get(j).getName() + (i+ ""));
                mTwoLayoutResponse2.setItemType(TwoLayoutResponse.ATTACHMENT);
                mTwoLayoutResponse2.setTwoBean(mTwoList1.get(j));
                mList.add(mTwoLayoutResponse2);
            }
        }
        setData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvTwoLayout.setLayoutManager(linearLayoutManager);
        mTwoLayoutAdapter = new TwoLayoutAdapter(mList);
        mRvTwoLayout.setAdapter(mTwoLayoutAdapter);


    }

    private void setData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
