package com.younchen.younsampleproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.commons.utils.DimenUtils;
import com.younchen.younsampleproject.commons.widget.RippleView;

public class NewSettingDefaultGuideActivity extends Activity {

    private static final int ANIM_REPEAT_TIMES = 3;

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
    private Handler mHandler;


    private int countDown = ANIM_REPEAT_TIMES;
    private View mImgSelectView;


    public static void start(Context context) {
        Intent starter = new Intent(context, NewSettingDefaultGuideActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_new_setting_default_guide);
        init();
        mAnimPostRunnable = new Runnable() {
            @Override
            public void run() {
                startAnimation(2);
            }
        };
        mHandler = new Handler(getMainLooper());
    }

    private void init() {
        mLauncherItemView = findViewById(R.id.launcher_view_container);
        mFingerView = (ImageView) findViewById(R.id.hand_img_view);
        mRippleView = (ImageView) findViewById(R.id.ripple_img_view);
        mLogImageContainer = findViewById(R.id.logo_img_container);
        mLogImageView = (ImageView) findViewById(R.id.logo_img);
        mAlwaysBtn = (RippleView) findViewById(R.id.btn_always);
        mCertainBtn = (TextView) findViewById(R.id.btn_got_it);
        mImgSelectView = findViewById(R.id.img_select_view);
        mCertainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDown = ANIM_REPEAT_TIMES;
        mHandler.postDelayed(mAnimPostRunnable, 200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAnimation();
        mHandler.removeCallbacks(mAnimPostRunnable);
    }


    public void startAnimation(final int repeatTime) {
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
                mFingerView.setVisibility(View.VISIBLE);
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


        ValueAnimator moveHandViewAnimator = fingerMoveAnimation(mFingerView, new Translation(0, 0), offsetX, offsetY);

        //step 3. show click animation
        AnimatorSet clickAnimationSet = new AnimatorSet();
        ValueAnimator clickAnimator = ValueAnimator.ofFloat(0.f, 1.f);
        clickAnimator.setDuration(200);
        clickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float alpha = (float) animation.getAnimatedValue();
                mRippleView.setAlpha(alpha);
            }
        });
        clickAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mRippleView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRippleView.setVisibility(View.GONE);

            }
        });

        ValueAnimator backGroundAnimator = ValueAnimator.ofFloat(1);
        backGroundAnimator.setDuration(500);
        backGroundAnimator.setInterpolator(new CycleInterpolator(0.5f));
        backGroundAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mImgSelectView.setVisibility(View.VISIBLE);
            }
        });
        backGroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float fraction = animation.getAnimatedFraction();
                float seed = 1.0f / 24 * fraction;
                YLog.i("fraction:", String.valueOf(fraction));
                mImgSelectView.setScaleX(1 + seed);
                mImgSelectView.setScaleY(1 + seed);
            }
        });
        clickAnimationSet.playTogether(clickAnimator, backGroundAnimator);
        final Rect alwaysBtnArea = new Rect();
        mAlwaysBtn.getGlobalVisibleRect(alwaysBtnArea);
        final int fingerMoveTarget2Top = alwaysBtnArea.top + alwaysBtnArea.height() / 2;

        final int offsetX2 = (alwaysBtnArea.left + alwaysBtnArea.width() / 2) - (launcherIconViewArea.left + launcherIconViewArea.width() / 2);
        final int offsetY2 = fingerMoveTarget2Top - fingerMoveTarget1Top;

        ValueAnimator moveHandViewAnimator2 = fingerMoveAnimation(mFingerView, new Translation(offsetX, offsetY), offsetX2, offsetY2);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(fingerViewShowAnimation, moveHandViewAnimator, clickAnimationSet, moveHandViewAnimator2);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAlwaysBtn.animateRipple(mAlwaysBtn.getWidth() / 2, mAlwaysBtn.getHeight() / 2);
                if (repeatTime > 0) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startAnimation(repeatTime - 1);
                        }
                    }, 1000);
                } else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NewSettingDefaultGuideActivity.this.finish();
                        }
                    }, 1000);
                }
            }
        });
        mAnimatorSet.start();
    }

    private ValueAnimator fingerMoveAnimation(final View view, final Translation translation, final int offsetX, final int offsetY) {
        ValueAnimator animator = ValueAnimator.ofFloat(0.f, 1.f);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float fraction = animation.getAnimatedFraction();
                view.setTranslationY(translation.translationY + offsetY * fraction);
                view.setTranslationX(translation.translationX + offsetX * fraction);
            }
        });
        return animator;
    }

    private void resetAnimationState() {
        mFingerView.setTranslationY(0);
        mFingerView.setTranslationX(0);
        mFingerView.setVisibility(View.INVISIBLE);
        mImgSelectView.setVisibility(View.GONE);
    }


    public void stopAnimation() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet.removeAllListeners();
        }
    }

    class Translation {
        public float translationX;
        public float translationY;

        public Translation(float x, float y) {
            translationX = x;
            translationY = y;
        }
    }
}
