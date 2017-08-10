package com.younchen.younsampleproject.commons.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/4/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getFragmentLayoutContainerId();

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            this.finish();
        }
    }
}
