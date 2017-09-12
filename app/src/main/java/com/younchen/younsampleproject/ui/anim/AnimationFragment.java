package com.younchen.younsampleproject.ui.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.Button;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.fragment.BaseFragment;
import com.younchen.younsampleproject.commons.log.YLog;

import butterknife.BindView;

/**
 * Created by yinlongquan on 2017/9/12.
 */

public class AnimationFragment extends BaseFragment {

    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.anim_view)
    View animView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.animtion, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAnimation();
            }
        });
    }

    private void playAnimation() {
        ValueAnimator backGroundAnimator = ValueAnimator.ofFloat(1);
        backGroundAnimator.setDuration(500);
        backGroundAnimator.setInterpolator(new CycleInterpolator(0.5f));
        backGroundAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        backGroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float fraction = animation.getAnimatedFraction();
                float seed = 1.0f / 24 * fraction;
                YLog.i("fraction:", String.valueOf(fraction));
                animView.setScaleX(1 + seed);
                animView.setScaleY(1 + seed);
            }
        });
        backGroundAnimator.start();
    }

    @Override
    public void onBackKeyPressed() {

    }
}
