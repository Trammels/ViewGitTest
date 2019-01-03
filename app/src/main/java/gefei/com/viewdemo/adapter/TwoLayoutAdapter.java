package gefei.com.viewdemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import gefei.com.viewdemo.R;
import gefei.com.viewdemo.entity.TwoLayoutResponse;

/**
 * Created by gefei on 2018/9/6.
 */

public class TwoLayoutAdapter extends BaseMultiItemQuickAdapter<TwoLayoutResponse, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TwoLayoutAdapter(List<TwoLayoutResponse> data) {
        super(data);
        addItemType(TwoLayoutResponse.HEADER, R.layout.view_cycle_two_layout);
        addItemType(TwoLayoutResponse.ATTACHMENT, R.layout.view_cycle_two_layout2);
//        addItemType(TwoLayoutResponse.BOTTOM, R.layout.view_cycle_two_layout2);
    }

    @Override
    protected void convert(BaseViewHolder helper, TwoLayoutResponse item) {
        switch (helper.getItemViewType()) {
            case TwoLayoutResponse.HEADER:
                helper.setText(R.id.tv_name, item.getName()+"");
                break;
            case TwoLayoutResponse.ATTACHMENT:
                helper.setText(R.id.tv_name, item.getTwoBean().getName()+"");
                break;
        }

    }
}
