package gefei.com.viewdemo.uianim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mls.baseProject.ui.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gefei.com.viewdemo.R;

public class UIPull extends BaseActivity {

    private String[] mVals = new String[]
                {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                            "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                            "Android", "Weclome Hello", "Button Text", "TextView"};
    private TagFlowLayout mFlowLayout;
    private List<String> mdatas;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_uipull);
    }

    @Override
    protected void initData(Bundle bundle) {
        mdatas = new ArrayList<>();
        mdatas.add("Android");
        mdatas.add("Hello");
        mdatas.add("Android1111111111111111");
        mdatas.add("Android2");
        mdatas.add("Android3333333");
        mdatas.add("Android444");
        mdatas.add("Android555");
        mdatas.add("Android666111111");
        mdatas.add("Android7777");
        mdatas.add("Android8888");
        mdatas.add("Android00000000000");


        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        //mFlowLayout.setMaxSelectCount(3);
        mFlowLayout.setAdapter(new TagAdapter<String>(mdatas) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return s.equals("Android");
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Toast.makeText(UIPull.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                showToast("" + mdatas.get(position));
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                UIPull.this.setTitle("choose:" + selectPosSet.toString());
            }
        });
    }

}
