package com.example.justmeat.homepage.Java;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.example.justmeat.R;

/*
    Classe utilizzata per la creazione di una progressBar custom per indicare lo stato di un ordine
    Per indicare il progresso della barra si usa setProgress(int value)
    Il secondo metodo che usa il boolean è per la gestione delle animazioni
    Per il progresso si usano i seguenti treshold:
        0 solo il primo cerchio è verde
        >33 il secondo cerchio diventa verde
        >66 il terzo diventa verde
        >100 il quarto diventa verde (barra completa)
    Utilizza valori leggermente superiori al threshold per evitare piccoli errori grafici
    (prova funzionante con set di valori {5,40,70,100}

 */

public class CustomProgressBar extends View {

    private int progress;
    int goal=50;
    int barHeight;
    int goalIndicatorRadius;
    Paint progressPaint;
    ValueAnimator barAnimator;

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void setProgress(final int progress, boolean animate){
        if (animate) {
            barAnimator = ValueAnimator.ofFloat(0, 1);

            barAnimator.setDuration(700);

            // reset progress without animating
            setProgress(0, false);

            barAnimator.setInterpolator(new DecelerateInterpolator());

            barAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float interpolation = (float) animation.getAnimatedValue();
                    setProgress((int) (interpolation * progress), false);
                }
            });

            if (!barAnimator.isStarted()) {
                barAnimator.start();
            }
        } else {
            this.progress = progress;
            postInvalidate();
        }
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }

    public void init(AttributeSet attrs){
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, 0, 0);
        try {
            setBarHeight(typedArray.getDimensionPixelOffset(R.styleable.CustomProgressBar_barHeight, 15));
            setGoalIndicatorRadius(typedArray.getDimensionPixelOffset(R.styleable.CustomProgressBar_goalIndicatorRadius, 30));
        } finally {
            typedArray.recycle();
        }
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    public void setGoalIndicatorRadius(int goalIndicatorRadius) {
        this.goalIndicatorRadius = goalIndicatorRadius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize= MeasureSpec.getSize(heightMeasureSpec);
        int height;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                // we must be exactly the given size
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                // we can not be bigger than the specified height
                height = (int) Math.min(goalIndicatorRadius*2, heightSize);
                break;
            default:
                // we can be whatever height want
                height = (int) goalIndicatorRadius*2;
                break;
        }
        height = (int) goalIndicatorRadius*4;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfHeight = getHeight() / 2;
        int progressEndX = (int) (getWidth() * progress / 100f);

        int color1=getResources().getColor(R.color.barColor);
        int color2=getResources().getColor(R.color.unfilledbarColor);
        int color3=getResources().getColor(R.color.unfilledbarColor);
        int color4=getResources().getColor(R.color.unfilledbarColor);

        if(progress>33){
            color2=getResources().getColor(R.color.barColor);
        }
        if (progress>66){
            color3=getResources().getColor(R.color.barColor);
        }
        if(progress==100){
            color4=getResources().getColor(R.color.barColor);
        }

        // draw the part of the bar that's filled
        progressPaint.setStrokeWidth(barHeight);
        progressPaint.setColor(getResources().getColor(R.color.barColor));
        canvas.drawLine(halfHeight, halfHeight, progressEndX, halfHeight, progressPaint);

        // draw the unfilled section
        progressPaint.setColor(getResources().getColor(R.color.unfilledbarColor));
        if(progressEndX==0){
            progressEndX=halfHeight;
        }
        canvas.drawLine(progressEndX, halfHeight, getWidth()-halfHeight, halfHeight, progressPaint);

        // draw the goal indicator
        float indicatorPosition1 = getWidth() * 0 / 100f;
        indicatorPosition1+=halfHeight;
        progressPaint.setColor(color1);
        canvas.drawCircle(indicatorPosition1, halfHeight, halfHeight/2, progressPaint);

        float indicatorPosition2 = getWidth() * 33 / 100f;
        progressPaint.setColor(color2);
        canvas.drawCircle(indicatorPosition2,halfHeight, halfHeight/2, progressPaint);

        float indicatorPosition3 = getWidth() *66 / 100f;
        progressPaint.setColor(color3);
        canvas.drawCircle(indicatorPosition3,halfHeight, halfHeight/2, progressPaint);

        float indicatorPosition4 = getWidth() * 100 / 100f;
        indicatorPosition4-=halfHeight;
        progressPaint.setColor(color4);
        canvas.drawCircle(indicatorPosition4,halfHeight, halfHeight/2, progressPaint);


    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        // save our progress
        bundle.putInt("progress", progress);

        // be sure to save all other instance state that may exist
        bundle.putParcelable("superState", super.onSaveInstanceState());

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            // restore our progress
            setProgress(bundle.getInt("progress"));

            // restore all other state
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }
}

