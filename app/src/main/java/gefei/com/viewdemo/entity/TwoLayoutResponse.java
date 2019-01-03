package gefei.com.viewdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by gefei on 2018/9/6.
 */

public class TwoLayoutResponse implements MultiItemEntity {
    public static final int HEADER = 1;
    public static final int ATTACHMENT = 2;
    public static final int BOTTOM = 3;
    private int itemType;
    private String name;
    private TwoBean mTwoBean;
    private OneBean mOneBean;

    public OneBean getOneBean() {
        return mOneBean;
    }

    public void setOneBean(OneBean oneBean) {
        mOneBean = oneBean;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TwoBean getTwoBean() {
        return mTwoBean;
    }

    public void setTwoBean(TwoBean twoBean) {
        mTwoBean = twoBean;
    }

    public TwoLayoutResponse(String name, TwoBean twoBean) {
        this.name = name;
        mTwoBean = twoBean;
    }

    public TwoLayoutResponse() {
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public static class TwoBean {
        private String name;
        private boolean isSelect;

        public TwoBean() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public TwoBean(String name) {
            this.name = name;
        }
    }

    public class OneBean {
        private String name;
        private boolean isSelect;

        public OneBean() {
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public OneBean(String name) {
            this.name = name;
        }
    }

}
