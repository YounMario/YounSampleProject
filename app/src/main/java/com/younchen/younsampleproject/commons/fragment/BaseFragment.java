package com.younchen.younsampleproject.commons.fragment;


import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.younchen.younsampleproject.commons.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/11.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    private boolean mBackPressEnable;


    public void show(BaseActivity activity) {
        long currentTime = System.currentTimeMillis();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(activity.getFragmentLayoutContainerId(), this);
        transaction.addToBackStack(BaseFragment.class.getSimpleName() + String.valueOf(currentTime));
        transaction.commit();
    }

    public void setEnableBackPress(boolean enable) {
        this.mBackPressEnable = enable;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
        ButterKnife.bind(this, mRootView);
        mRootView.setFocusableInTouchMode(true);
        mRootView.requestFocus();
        //一种监听back键的方法
        if(mBackPressEnable) {
            mRootView.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            onBackKeyPressed();
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    public abstract void onBackKeyPressed();


}
