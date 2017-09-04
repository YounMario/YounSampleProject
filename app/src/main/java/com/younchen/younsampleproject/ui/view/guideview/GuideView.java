package com.younchen.younsampleproject.ui.view.guideview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.utils.DimenUtils;
import com.younchen.younsampleproject.commons.widget.RippleView;

/**
 * Created by yinlongquan on 2017/9/1.
 */

public class GuideView extends RelativeLayout {

    private Context mContext;

    private ImageView mFingerView;
    private ImageView mRippleView;
    private ImageView mLogImageView;

    private View mLauncherItemView;
    private View mLogImageContainer;

    private RippleView mAlwaysBtn;
    private AnimatorSet mAnimatorSet;
    private WindowManager mWindowManager;
    private TextView mCertainBtn;

    private Runnable mAnimPostRunnable;


    public GuideView(Context context) {
        this(context, null);
    }

    public GuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_guide_view, this, true);

        mLauncherItemView = findViewById(R.id.launcher_view_container);
        mFingerView = (ImageView) findViewById(R.id.hand_img_view);
        mRippleView = (ImageView) findViewById(R.id.ripple_img_view);
        mLogImageContainer = findViewById(R.id.logo_img_container);
        mLogImageView = (ImageView) findViewById(R.id.logo_img);
        mAlwaysBtn = (RippleView) findViewById(R.id.btn_always);

        mCertainBtn = (TextView) findViewById(R.id.btn_got_it);
        mCertainBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onFinishInflate() {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    public void show() {
        mWindowManager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.format = PixelFormat.TRANSPARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
        mWindowManager.addView(this, layoutParams);
        mAnimPostRunnable = new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        };
        postDelayed(mAnimPostRunnable, 200);
    }

    public void hide() {
        stopAnimation();
        if (this.isAttachedToWindow()) {
            mWindowManager.removeView(this);
        }
        removeCallbacks(mAnimPostRunnable);
    }

    public void startAnimation() {
        resetAnimationState();
        //step 1. show finger view
        ValueAnimator fingerViewShowAnimation = ValueAnimator.ofFloat(0.f, 1.f);
        fingerViewShowAnimation.setDuration(400);
        fingerViewShowAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float alpha = (float) animation.getAnimatedValue();
                mFingerView.setAlpha(alpha);
            }
        });
        fingerViewShowAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mFingerView.setVisibility(VISIBLE);
            }
        });


        //step 2. move finger view to ripple view
        Rect launcherIconViewArea = new Rect();
        mLogImageView.getGlobalVisibleRect(launcherIconViewArea);
        int fingerMoveTarget1Left = launcherIconViewArea.left + DimenUtils.dp2px(8);
        int fingerMoveTarget1Top = launcherIconViewArea.top + launcherIconViewArea.width() / 2;

        Rect fingerViewArea = new Rect();
        mFingerView.getGlobalVisibleRect(fingerViewArea);

        final int offsetY = fingerMoveTarget1Top - fingerViewArea.top;
        final int offsetX = fingerMoveTarget1Left - fingerViewArea.left;
        ValueAnimator moveHandViewAnimator = fingerMoveAnimation(mFingerView, offsetX, offsetY);

        //step 3. show click ripple
        ValueAnimator showClickRippleAnimator = ValueAnimator.ofFloat(0.f, 1.f);
        showClickRippleAnimator.setDuration(200);
        showClickRippleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float alpha = (float) animation.getAnimatedValue();
                mRippleView.setAlpha(alpha);
            }
        });
        showClickRippleAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mRippleView.setVisibility(VISIBLE);
                mLauncherItemView.setBackgroundResource(R.drawable.pic_selected);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRippleView.setVisibility(GONE);

            }
        });


        final Rect alwaysBtnArea = new Rect();
        mAlwaysBtn.getGlobalVisibleRect(alwaysBtnArea);
        final int fingerMoveTarget2Top = alwaysBtnArea.top + alwaysBtnArea.height() / 2;

        final int offsetX2 = (alwaysBtnArea.left + alwaysBtnArea.width() / 2) - (launcherIconViewArea.left + launcherIconViewArea.width() / 2);
        final int offsetY2 = fingerMoveTarget2Top - fingerMoveTarget1Top;
        ValueAnimator moveHandViewAnimator2 = fingerMoveAnimation(mFingerView, offsetX2, offsetY2);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(fingerViewShowAnimation, moveHandViewAnimator, showClickRippleAnimator, moveHandViewAnimator2);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAlwaysBtn.animateRipple(mAlwaysBtn.getWidth() / 2, mAlwaysBtn.getHeight() / 2);
            }
        });
        mAnimatorSet.start();
    }

    private ValueAnimator fingerMoveAnimation(final View fingerView, final int offsetX, final int offsetY) {
        final Translation translation = new Translation();
        ValueAnimator animator = ValueAnimator.ofFloat(0.f, 1.f);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                translation.translationX = fingerView.getTranslationX();
                translation.translationY = fingerView.getTranslationY();
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float fraction = animation.getAnimatedFraction();
                mFingerView.setTranslationY(translation.translationY + offsetY * fraction);
                mFingerView.setTranslationX(translation.translationX + offsetX * fraction);
            }
        });
        return animator;
    }

    private void resetAnimationState() {
        mFingerView.setTranslationY(0);
        mFingerView.setTranslationX(0);
        mFingerView.setVisibility(INVISIBLE);
        mLauncherItemView.setBackground(null);
    }


    public void stopAnimation() {
        if (mAnimatorSet != null) {
            mAnimatorSet.pause();
            mAnimatorSet.removeAllListeners();
        }
    }

    class Translation {
        public float translationX;
        public float translationY;
    }
}
