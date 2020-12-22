package com.example.justmeat.welcome;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.justmeat.R;

public class SplashActivity extends AppCompatActivity {
    ImageView nome, logo, change_back, logoyellow;
    ConstraintLayout base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nome = findViewById(R.id.splash_logo_name);
        logo = findViewById(R.id.splash_logo_img);
        logoyellow = findViewById(R.id.splash_logo_selected);
        logoyellow.setVisibility(View.INVISIBLE);
        logoyellow.setSelected(true);
        base = findViewById(R.id.splash_base);
        change_back = findViewById(R.id.splash_change_back);
        splashAnimation();
    }

    private void splashAnimation(){
//transition
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        PropertyValuesHolder translate1X = PropertyValuesHolder.ofFloat("translationX",2*width);
        ObjectAnimator move_back1 = ObjectAnimator.ofPropertyValuesHolder(change_back, translate1X);
        move_back1.setStartDelay(400);
        move_back1.setDuration(1500);
//change background color
        PropertyValuesHolder changeBackColor = PropertyValuesHolder.ofInt("backgroundColor", getResources().getColor(R.color.logo_color));
        ObjectAnimator changeback = ObjectAnimator.ofPropertyValuesHolder(base, changeBackColor);
        changeback.setStartDelay(1150);
        changeback.setDuration(10);
//hide loho pink
        PropertyValuesHolder hidelogopink = PropertyValuesHolder.ofInt("visibility",  View.INVISIBLE);
        ObjectAnimator hidelogopinkA = ObjectAnimator.ofPropertyValuesHolder(logo, hidelogopink);
        hidelogopinkA.setStartDelay(1150);
        hidelogopinkA.setDuration(2);
//show yellow logo
        PropertyValuesHolder showlogoYellow = PropertyValuesHolder.ofInt("visibility",  View.VISIBLE);
        ObjectAnimator showlogoYAnim = ObjectAnimator.ofPropertyValuesHolder(logoyellow, showlogoYellow);
        showlogoYAnim.setStartDelay(0);
        showlogoYAnim.setDuration(10);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(changeback).with(hidelogopinkA).with(move_back1);
        animatorSet.play(showlogoYAnim).after(hidelogopinkA);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

}
