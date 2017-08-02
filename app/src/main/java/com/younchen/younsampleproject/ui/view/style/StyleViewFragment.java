package com.younchen.younsampleproject.ui.view.style;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;

/**
 * Created by yinlongquan on 2017/7/21.
 */

public class StyleViewFragment extends BaseFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_style_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Drawable mDrawable = getActivity().getResources().getDrawable(R.drawable.main_back);
        ClipDrawable clipDrawable = new ClipDrawable(mDrawable);
        mRootView.setBackgroundDrawable(clipDrawable);
    }

    @Override
    public void onBackKeyPressed() {
    }
}
