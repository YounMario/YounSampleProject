package com.younchen.younsampleproject.ui.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;


/**
 * Created by 龙泉 on 2016/6/24.
 */
public class CustomLayout extends FrameLayout{

    private static final long ANIM_DURATION = 200;
    private float layoutTranslaction;
    private ObjectAnimator currentAnimator;
    private STATE state;
    private VelocityTracker mVelocityTracker;
    private View childView;

    private float distance ;
    private float preX;
    private float preY;
    private boolean isUp;
    private float lastTransalation;
    private View dimView;

    private Property<CustomLayout,Float> LAYOUT_TRANSLATION =new Property<CustomLayout, Float>(Float.class,"layoutTranslaction") {
        @Override
        public Float get(CustomLayout object) {
            return object.layoutTranslaction;
        }

        @Override
        public void set(CustomLayout object, Float value) {
            object.setLayoutTranslaction(value);
        }
    };



    public CustomLayout(Context context) {
        super(context);
        init();
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        Log.i("longquan","onFinishInflate");
        super.onFinishInflate();
    }

    @Override
    public void addView(View child) {
        setContent(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if(getChildCount() >0){
            throw new RuntimeException("already has a child");
        }
        if(params!=null)
           setContent(child,params);
        else
            setContent(child);
    }

    private void  init(){
        dimView = new View(getContext());
        dimView.setBackgroundColor(Color.BLACK);
        dimView.setAlpha(0);
        dimView.setVisibility(INVISIBLE);
    }

    public void setContent(View childView){
        super.addView(childView,-1,generateDefaultLayoutParams());
        super.addView(dimView, -1, generateDefaultLayoutParams());
    }

    public void setContent(View childView,ViewGroup.LayoutParams params){
        super.addView(childView,-1,params);
        super.addView(dimView, -1, generateDefaultLayoutParams());
    }

    public void setState(STATE state){
        this.state = state;
    }


    public void setLayoutTranslaction(float translaction){
        this.layoutTranslaction = translaction;
        childView.setTranslationY(layoutTranslaction);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.clear();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        childView = getChildAt(0);
        childView.setTranslationY(getHeight());
        mVelocityTracker = VelocityTracker.obtain();
        Log.i("longquan","onAttatch");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("longquan","onMesure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void expand(){
        cancelAnimation();
        ObjectAnimator objectAnimator =ObjectAnimator.ofFloat( this,LAYOUT_TRANSLATION ,0);
        objectAnimator.setDuration(ANIM_DURATION);
        objectAnimator.setInterpolator(new DecelerateInterpolator(1.0f));
        objectAnimator.addListener(new FinishAnimatorlistener(childView,true,0));
        objectAnimator.start();
        currentAnimator = objectAnimator;
        setState(STATE.EXPENDED);
        lastTransalation = 0;
    }

    private void peek(){
        cancelAnimation();
        ObjectAnimator objectAnimator =ObjectAnimator.ofFloat( this,LAYOUT_TRANSLATION ,getHeight());
        objectAnimator.setDuration(ANIM_DURATION);
        objectAnimator.setInterpolator(new DecelerateInterpolator(1.0f));
        objectAnimator.addListener(new FinishAnimatorlistener(childView,false,getHeight()));
        objectAnimator.start();
        currentAnimator = objectAnimator;
        setState(STATE.HIDE);
        lastTransalation = getHeight();
    }

    private void cancelAnimation(){
        if(currentAnimator!=null)
              currentAnimator.cancel();
    }

    public void setChildTranslation(float childTranslation) {
      childView.setTranslationY(childTranslation);
    }

    enum STATE{
        EXPENDED,HIDE;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        Log.i("longquan","interception invoked");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("longquan","interception down");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("longquan","interception action up");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("longquan","interception move");
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        if(childView == null)
            childView = getChildAt(0);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                preX = event.getX();
                preY = event.getY();
                distance = 0;
                mVelocityTracker.clear();
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.computeCurrentVelocity(200);
                float currentX = event.getX();
                float currentY = event.getY();
                distance = caclulateDistance(preX,preY,currentX,currentY);
                isUp = currentY-preY >0;
                layoutTranslaction = (isUp ? distance : -distance)+lastTransalation;
                Log.i("longquan","moving: distance"+distance+" get current transation:"+ childView.getTranslationY());
                setChildTranslation(layoutTranslaction);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.clear();
                if(layoutTranslaction < getHeight()*3 /4){
                    expand();
                }else {
                    peek();
                }
                break;
        }
        return true;
    }

    private float caclulateDistance(float sx,float sy ,float ex, float ey){
        return (float) Math.sqrt(Math.pow(ex -sx,2) +Math.pow(ey -sy,2) );
    }

    public static class FinishAnimatorlistener extends AnimatorListenerAdapter {

        private boolean isUP;
        private View child;
        private int endTranslation;

        public FinishAnimatorlistener(View child,boolean isUp,int endTranslation){
            this.isUP = isUp;
            this.child = child;
            this.endTranslation = endTranslation;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            child.setTranslationY(endTranslation);
        }
    }


}
