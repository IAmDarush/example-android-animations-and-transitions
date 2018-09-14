package com.example.sampleandroidanimationsandtransitions;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvHello;
    Button btnHorizontal;
    Button btnVertical;
    Button btnDiagonal;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHello = findViewById(R.id.text_main_hello);
        btnHorizontal = findViewById(R.id.button_main_horizontal);
        btnVertical = findViewById(R.id.button_main_vertical);
        btnDiagonal = findViewById(R.id.button_main_diagonal);
        btnReset = findViewById(R.id.button_main_reset);

        btnHorizontal.setOnClickListener(this);
        btnVertical.setOnClickListener(this);
        btnDiagonal.setOnClickListener(this);
        btnReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_main_reset:
                resetTheView(tvHello);
                break;
            case R.id.button_main_horizontal:
                animateHorizontally(tvHello);
                break;
            case R.id.button_main_vertical:
                animateVertically(tvHello);
                break;
            case R.id.button_main_diagonal:
                animateDiagonally(tvHello);
                break;

        }
    }

    private void resetTheView(View view) {
        view.setTranslationX(btnReset.getTranslationX());
        view.setTranslationY(btnReset.getTranslationY());
    }

    private void animateHorizontally(final View v) {
        ValueAnimator animator = ValueAnimator.ofInt(0,
                getScreenWith() - getAbsoluteX(v) - v.getWidth());
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                int animatedValue = (int) valueAnimator.getAnimatedValue();
                v.setTranslationX(animatedValue);
            }
        });
    }

    private void animateVertically(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY",
                getScreenHeight() - getAbsoluteY(v) - v.getHeight());
        animator.setDuration(1000);
        animator.start();
    }

    private void animateDiagonally(View v) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(v, "x", getScreenHeight());
        ObjectAnimator animY = ObjectAnimator.ofFloat(v, "y", getScreenWith());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animX, animY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    private void animateUsingAnimatorSet(Object target) {
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, .36f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("rotation",
                kf0, kf1, kf2);
        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(target, valuesHolder);
        rotationAnim.setDuration(5000);
    }

    private int getScreenWith() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    private int getScreenHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    private int getAbsoluteX(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        return location[0];
    }

    private int getAbsoluteY(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        return location[1];
    }
}
