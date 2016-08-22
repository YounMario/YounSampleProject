package com.younchen.younsampleproject.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.younchen.younsampleproject.R;

public class AnimationActivity extends AppCompatActivity {

    private ImageView imageView;
    private ObjectAnimator windMileAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        imageView = (ImageView) findViewById(R.id.windmile);
        windMileAnimation = ObjectAnimator.ofFloat(imageView, View.ROTATION,0, 360);
        windMileAnimation.setDuration(1000);
        windMileAnimation.setInterpolator(new LinearInterpolator());
        windMileAnimation.setRepeatCount(ValueAnimator.INFINITE);
        windMileAnimation.setRepeatMode(ValueAnimator.INFINITE);
        windMileAnimation.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        windMileAnimation.cancel();
    }
}
