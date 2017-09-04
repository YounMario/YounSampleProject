package com.younchen.younsampleproject.sys.process.bean;

import android.graphics.Bitmap;

/**
 * Created by yinlongquan on 2017/8/18.
 */

public class ProcessItem {

    private String mPackageName;
    private String mLabel;
    private Bitmap mIcon;

    public String getmPackageName() {
        return mPackageName;
    }

    public void setPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public void setIcon(Bitmap mIcon) {
        this.mIcon = mIcon;
    }
}
