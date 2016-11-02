package com.carporange.cloudmusic.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.carporange.cloudmusic.R;
import com.carporange.cloudmusic.ui.base.BaseActivity;

import butterknife.BindView;

import static com.carporange.cloudmusic.R.id.textView;

/**
 * Created by liuhui on 2016/6/27.
 */
public class SplashActivity extends BaseActivity implements Runnable {
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.textView)
    TextView nameTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏navigation即底部栏
        getWindow().getDecorView().
                setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initActionBar() {

    }

    @Override
    public void initViews() {
        fab.postDelayed(this, 200);
        /*AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1.0f);
        alphaAnimation.setDuration(2000);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1.0f, 0.6f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        mLinearLayout.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
    }

    @Override
    public void run() {
        final View parentView = (View) fab.getParent();
        float scale = (float) (Math.sqrt(parentView.getHeight() * parentView.getHeight() + parentView.getWidth() * parentView.getWidth()) / fab.getHeight());
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", scale);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", scale);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(fab, scaleX, scaleY).setDuration(1900);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                parentView.setBackgroundColor(ContextCompat.getColor(SplashActivity.this, R.color.colorPrimary));
//                fab.setVisibility(View.GONE);
//                nameTextView.setVisibility(View.VISIBLE);
            }
        });
        PropertyValuesHolder holderA = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder holderYm = PropertyValuesHolder.ofFloat("translationY", 0, 300);
        ObjectAnimator textAnimator = ObjectAnimator.ofPropertyValuesHolder(textView, holderA, holderYm).setDuration(1000);
        textAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        textAnimator.setStartDelay(800);

        textAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });
        objectAnimator.start();
        textAnimator.start();
    }
}
