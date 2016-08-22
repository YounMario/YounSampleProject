package com.younchen.younsampleproject.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.ui.layout.ScrollableLayout;

public class ScrollerViewSampleActivity extends AppCompatActivity {

    private ScrollableLayout scrollLayout;
    private int[] colors= {Color.BLUE,Color.GREEN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_view_sample);

        scrollLayout= (ScrollableLayout) findViewById(R.id.scrollLayout);
        for(int i=0;i<2;i++){
            View v = new View(this);
            v.setBackgroundColor(colors[i]);
            scrollLayout.addView(v);
        }
    }


}
