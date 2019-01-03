package gefei.com.viewdemo.entity;

import com.mls.baseProject.entity.response.common.CommResponse;

/**
 * Created by gefei on 2018/4/23.
 */

public class UIFlexBoxRecycleViewResponse extends CommResponse {
    private String name;
    private boolean select;

    public UIFlexBoxRecycleViewResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
