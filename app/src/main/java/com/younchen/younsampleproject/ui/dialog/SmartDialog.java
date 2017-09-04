
package com.younchen.younsampleproject.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.utils.DimenUtils;


public class SmartDialog extends Dialog {
    private View mView;
    private int mWidth;
    private int mHeight;
    protected static float sWidthRatio = 0.9f;
    protected static float sBsWidthRatio = 1.0f; //bottom style

    public SmartDialog(Context context) {
        this(context, R.style.SmartDialogStyle);
    }

    public SmartDialog(Context context, int type) {
        super(context, type);
        // 设置对话框背景透明度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        updateDialogBg(lp);
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //DeviceInfoUtils.isTablet()
        if (false) {
            sWidthRatio = 0.6f;
            sBsWidthRatio = 0.7f;
        }
    }

    /**
     * default 0.6
     */
    public void updateDialogBg(WindowManager.LayoutParams lp) {
        lp.dimAmount = 0.6f;
    }

    public void changeDialogLayout(boolean isChangeHeight, ListView listView) {
        final Resources resources = getContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.gravity = Gravity.CENTER;
        p.width = dm.widthPixels;
        if (listView != null && isChangeHeight) {
            final float scale = dm.density;
            final int itemHeight = resources.getDimensionPixelSize(
                    DimenUtils.dp2px(55));
            float src_height_dp = dm.heightPixels / scale;

            src_height_dp -= 140.0f;
            BaseAdapter baseAdapter = (BaseAdapter) listView.getAdapter();
            int maxHeight = baseAdapter == null ? itemHeight * 8 : itemHeight
                    * baseAdapter.getCount();
            final int height = (int) (src_height_dp * scale + 0.5f);
            p.height = height > maxHeight ? maxHeight : height;
        }
        getWindow().setAttributes(p);
    }

    public FrameLayout addMaskView(View view) {
        FrameLayout rootView = new FrameLayout(getContext());

        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        rootView.addView(view, getContentLayoutParams());
        return rootView;
    }

    public FrameLayout.LayoutParams getContentLayoutParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        return params;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(getContext())
                .inflate(layoutResID, null);
        this.setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        mView = view;
        FrameLayout rootLayout = addMaskView(view);
        view.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mWidth = view.getMeasuredWidth();
        mHeight = view.getMeasuredHeight();
        super.setContentView(rootLayout);
    }

    protected void setMargin(FrameLayout.LayoutParams mParams, int m, DisplayMetrics dm){
        if(mParams != null && dm != null) {
            mParams.setMargins(-(m - dm.widthPixels) / 2, -(m - dm.heightPixels) / 2, 0, 0);
        }
    }


    public void setContentView(View view, Boolean isApkPreInstall) {
        mView = view;
        if (isApkPreInstall) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.dimAmount = 0.0f;
            getWindow().setAttributes(lp);

            final Resources resources = getContext().getResources();
            final DisplayMetrics dm = resources.getDisplayMetrics();

            int m = (int) Math.round(Math
                    .sqrt(dm.widthPixels * dm.widthPixels + dm.heightPixels * dm.heightPixels));
            FrameLayout.LayoutParams mParams = new FrameLayout.LayoutParams(
                    m, m);
            setMargin(mParams,m,dm);
            //mParams.setMargins(-(m - dm.widthPixels) / 2, -(m - dm.heightPixels) / 2,0 , 0);
            mWidth = m;
            mHeight = m;

            super.setContentView(view, mParams);
        } else {
            FrameLayout rootLayout = addMaskView(view);
            view.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mWidth = view.getMeasuredWidth();
            mHeight = view.getMeasuredHeight();
            super.setContentView(rootLayout);
        }
    }

    public View getContentView() {
        return mView;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    @Override
    public void show() {
        super.show();
    }
}
