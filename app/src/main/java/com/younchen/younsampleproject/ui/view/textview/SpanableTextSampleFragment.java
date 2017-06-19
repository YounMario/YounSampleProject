package com.younchen.younsampleproject.ui.view.textview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.utils.DimenUtils;

import butterknife.BindBitmap;
import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/16.
 */

public class SpanableTextSampleFragment extends BaseFragment {

    @BindView(R.id.txt_content)
    TextView textView;

    @BindBitmap(R.drawable.ic_star_green)
    Bitmap mStarImage;
    private final int STAR_IMAGE_SIZE= DimenUtils.dp2px(20);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_spanable_text, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        SpannableString spannableString = new SpannableString("*");
        replaceTextAsStar(spannableString);
        textView.setText(spannableString);
    }

    private void replaceTextAsStar(SpannableString spannableString) {
        //Not working when text textAllCaps is true
        textView.setAllCaps(false);
        ImageSpan imageSpan = new ImageSpan(getActivity(), mStarImage);
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }


    @Override
    public void onBackKeyPressed() {

    }
}
