package com.younchen.younsampleproject.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.younchen.younsampleproject.R;

public class SvgIconSampleActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_icon_sample);
        textView = (TextView) findViewById(R.id.txt_content);

        String txt = getResources().getString(R.string.tip_string);
        String license = getResources().getString(R.string.license);

        String content = txt + license;
        int contentLentrh = license.length();


        SpannableString spanString = new SpannableString(content);
        // spanString.length()- contentLentrh-1, spanString.length()-1
        spanString.setSpan(new ForegroundColorSpan(Color.BLUE), spanString.length() - contentLentrh, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new UnderlineSpan(),spanString.length() - contentLentrh, spanString.length(), 0);
        textView.setText(spanString);
    }
}
