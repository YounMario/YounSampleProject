package com.younchen.younsampleproject.ui.window;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.younsampleproject.commons.utils.DimenUtils;
import com.younchen.younsampleproject.commons.utils.SystemUiUtils;


/**
 * Created by Administrator on 2017/3/28.
 */
public class KeyBoardController {

    private View mBottom;
    private KeyBoardShowListener mKeyBoardShowListener;
    private static String TAG = "KeyBoardController";
    private View mRootView;


    int screenHeight = DimenUtils.getRealHeight();
    int defaultOffset = DimenUtils.dp2px(4);
    int navigationBar;
    private View.OnLayoutChangeListener mOnLayoutChangeListner;


    /**
     * 布局一定是在Activity里面的才可以调用
     *
     * @param bottom
     */
    public KeyBoardController(View rootView, View bottom) {
        this.mBottom = bottom;
        this.mRootView = rootView;
        init();
    }

    private void init() {
        mOnLayoutChangeListner = new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Rect r = getFrameVisiblePart();
                if (isMayBeKeyBoard(r)) {
                    onKeyBoardShow(r.bottom, screenHeight - r.bottom);
                } else if (isKeyBoardMaybeHide(r)) {
                    onKeyBoardHide();
                }
            }
        };
        mRootView.addOnLayoutChangeListener(mOnLayoutChangeListner);
        navigationBar = SystemUiUtils.getNavigationBarHeight(mRootView.getContext());
    }

    private void onKeyBoardShow(int keyboardTop, int keyboardHeight) {
        float moveDistance = getMoveDistance(keyboardTop);
        View target = mBottom;
        if (Math.abs(moveDistance) == Math.abs(target.getTranslationY())) {
            return;
        }
        moveViewPartFromCoverByKeyBoard(mBottom, (int) moveDistance);
        if (mKeyBoardShowListener != null) {
            mKeyBoardShowListener.onKeyBoardShow(keyboardTop, keyboardHeight);
        }
    }

    private void onKeyBoardHide() {
        moveViewPartFromCoverByKeyBoard(mBottom, 0);
        if (mKeyBoardShowListener != null) {
            mKeyBoardShowListener.onKeyBoardHide();
        }
    }

    interface KeyBoardShowListener {

        void onKeyBoardShow(int keyboardTop, int keyboardHeight);

        void onKeyBoardHide();
    }

    public void setKeyBoardShowListener(KeyBoardShowListener keyBoardShowListener) {
        this.mKeyBoardShowListener = keyBoardShowListener;
    }

    private float getMoveDistance(int keyboardTop) {
        int[] positionOnScreen = new int[2];
        View target = mBottom;
        target.getLocationOnScreen(positionOnScreen);
        int originBottom = (int) (positionOnScreen[1] + target.getMeasuredHeight() - target.getTranslationY());
        return (originBottom <= keyboardTop ? 0 : originBottom - keyboardTop);
    }

    private void moveViewPartFromCoverByKeyBoard(View bottom, int moveDistance) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bottom.getLayoutParams();
        params.bottomMargin = Math.abs(moveDistance);
        bottom.setLayoutParams(params);
    }


    private Rect getFrameVisiblePart() {
        Rect r = new Rect();
        mRootView.getWindowVisibleDisplayFrame(r);
        return r;
    }

    private boolean isKeyBoardMaybeHide(Rect r) {
        return r.bottom >= screenHeight - DimenUtils.dp2px(100);
    }

    private boolean isMayBeKeyBoard(Rect r) {
        int rectHeight = r.bottom - r.top;
        return rectHeight < screenHeight && (screenHeight - r.bottom) > DimenUtils.dp2px(100);
    }

    public void onDestroy() {
        if (mRootView != null) {
            mRootView.removeOnLayoutChangeListener(mOnLayoutChangeListner);
        }
    }
}
