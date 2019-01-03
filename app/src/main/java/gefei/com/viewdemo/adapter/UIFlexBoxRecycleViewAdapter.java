package gefei.com.viewdemo.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import gefei.com.viewdemo.R;
import gefei.com.viewdemo.entity.UIFlexBoxRecycleViewResponse;

/**
 * Created by gefei on 2018/4/23.
 */

public class UIFlexBoxRecycleViewAdapter extends BaseQuickAdapter<UIFlexBoxRecycleViewResponse,BaseViewHolder> {
    public UIFlexBoxRecycleViewAdapter(@Nullable List<UIFlexBoxRecycleViewResponse> data) {
        super(R.layout.view_cycle_flexbox, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UIFlexBoxRecycleViewResponse item) {
        FlexboxLayout flexboxLayout = helper.getView(R.id.flexboxLayout);

        ViewGroup.LayoutParams lp = flexboxLayout.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(0.8f);

        }
        TextView textView = helper.getView(R.id.tv);
        textView.setText(item.getName());

    }
}
