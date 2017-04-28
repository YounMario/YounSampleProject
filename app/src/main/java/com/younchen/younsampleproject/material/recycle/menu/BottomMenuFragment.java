package com.younchen.younsampleproject.material.recycle.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.material.widget.BottomShareMenu;

/**
 * Created by Administrator on 2017/4/28.
 */

public class BottomMenuFragment extends BaseFragment {

    private Button mButton;
    private BottomShareMenu mBottomShareMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setEnableBackPress(true);
        return inflater.inflate(R.layout.layout_bottom_menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view) {
        mBottomShareMenu = new BottomShareMenu(getActivity());
        mButton = (Button) view.findViewById(R.id.btn_share);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomShareMenu.isShowing()) {
                    mBottomShareMenu.hide();
                } else {
                    mBottomShareMenu.show();
                }
            }
        });
    }


    @Override
    public void onBackKeyPressed() {
        if (mBottomShareMenu.isShowing()) {
            mBottomShareMenu.hide();
        }
    }
}
