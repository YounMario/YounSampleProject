package com.younchen.younsampleproject.sys;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.utils.DimenUtils;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ItemOptionMenu {

    private final View mRootView;
    private PopupWindow mPopWindow;
    private Context mContext;
    private TextView mBtnDelete;

    private final int DEFAULT_MARGIN_BOTTOM_FROM_TARGET = DimenUtils.dp2px(18);
    private final int DEFAULT_MARGIN_RIGHT_FROM_TARGET = DimenUtils.dp2px(58);

    private OnDeleteBtnClickListener onDeleteBtnClickListener;

    public ItemOptionMenu(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_menu, null);
        mBtnDelete = (TextView) mRootView.findViewById(R.id.btn_del);
        mPopWindow = new PopupWindow(context);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopWindow.setContentView(mRootView);
    }

    public void show(View target, final Object item) {
        if (target == null) {
            return;
        }
        if (mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteBtnClickListener != null) {
                    onDeleteBtnClickListener.onDelete(item);
                }
                if(mPopWindow.isShowing()){
                    mPopWindow.dismiss();
                }
            }
        });
        Rect position = locateView(target);
        mPopWindow.showAtLocation(target, 0, position.right - DEFAULT_MARGIN_RIGHT_FROM_TARGET, position.bottom - DEFAULT_MARGIN_BOTTOM_FROM_TARGET);
    }

    private Rect locateView(View v) {
        int[] loc_int = new int[2];
        v.getLocationOnScreen(loc_int);
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }

    public void setOnDeleteBtnClickListener(OnDeleteBtnClickListener onDeleteBtnClickListener) {
        this.onDeleteBtnClickListener = onDeleteBtnClickListener;
    }

    public interface OnDeleteBtnClickListener {
        void onDelete(Object obj);
    }

}
