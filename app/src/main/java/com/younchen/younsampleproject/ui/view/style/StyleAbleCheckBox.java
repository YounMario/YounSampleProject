package com.younchen.younsampleproject.ui.view.style;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.younchen.younsampleproject.R;

/**
 * Created by yinlongquan on 2017/7/21.
 */

public class StyleAbleCheckBox extends CheckBox {

    public StyleAbleCheckBox(Context context) {
        this(context, null);
    }

    public StyleAbleCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setBackground(getDrawableSelector(setDrawableColor(getSelectorDrawables())));
        setButtonDrawable(null);
    }

    private StateListDrawable getDrawableSelector(Drawable[] drawables) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_checked}, drawables[1]);
        res.addState(new int[]{}, drawables[0]);
        return res;
    }

    private Drawable[] getSelectorDrawables() {
        return new Drawable[]{
                getResources().getDrawable(R.drawable.ic_check_box),
                getResources().getDrawable(R.drawable.ic_check_box_normal)
        };
    }

    private Drawable[] setDrawableColor(Drawable[] drawables) {
        drawables[0].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        drawables[1].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        return drawables;
    }
}
