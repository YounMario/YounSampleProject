package com.younchen.younsampleproject.material.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.anim.DefaultAnimationListener;
import com.younchen.younsampleproject.commons.widget.AbstractWindow;
import com.younchen.younsampleproject.material.Constants;
import com.younchen.younsampleproject.material.adapter.BottomMenuAdapter;
import com.younchen.younsampleproject.material.item.GridItemDecoration;
import com.younchen.younsampleproject.ui.view.ExpandableLayout.CustomGridLayoutManager;

/**
 * Created by Administrator on 2017/4/28.
 */

public class BottomShareMenu extends AbstractWindow {

    private RecyclerView mRecycleView;
    private BottomMenuAdapter mBottomMenuAdapter;
    private Animator mPopWindowAnimation;

    public BottomShareMenu(Context context) {
        super(context);
        setContentView(R.layout.layout_bottom_menu);
    }

    @Override
    protected void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.bottom_recycle_view);
        CustomGridLayoutManager gridLayoutManager = new CustomGridLayoutManager(mContext, 3);
        gridLayoutManager.setScrollEnabled(false);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mBottomMenuAdapter = new BottomMenuAdapter(mContext);
        mBottomMenuAdapter.setData(Constants.APPS);

        mRecycleView.setAdapter(mBottomMenuAdapter);
        mRecycleView.addItemDecoration(new GridItemDecoration(4));
    }

    @Override
    public void show() {
        showWindow();
        if (mPopWindowAnimation != null && mPopWindowAnimation.isRunning()) {
            return;
        }
        mPopWindowAnimation = ObjectAnimator.ofFloat(mRootView, View.TRANSLATION_Y, mRootView.getMeasuredHeight(), 0);
        mPopWindowAnimation.addListener(new DefaultAnimationListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                mRootView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        mPopWindowAnimation.setDuration(300);
        mPopWindowAnimation.start();
    }

    @Override
    public void hide() {
        if (mPopWindowAnimation != null && mPopWindowAnimation.isRunning()) {
            return;
        }
        mPopWindowAnimation = ObjectAnimator.ofFloat(mRootView, View.TRANSLATION_Y, 0, mRootView.getMeasuredHeight());
        mPopWindowAnimation.addListener(new DefaultAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRootView.setVisibility(View.INVISIBLE);
                hideWindow();
            }
        });
        mPopWindowAnimation.setDuration(300);
        mPopWindowAnimation.start();
    }


    @Override
    public WindowManager.LayoutParams getDefaultLayoutParams() {
        WindowManager.LayoutParams layoutParams = super.getDefaultLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        return layoutParams;
    }


}
