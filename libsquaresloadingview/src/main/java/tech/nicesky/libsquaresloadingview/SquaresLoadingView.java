/**
 * @class tech.nicesky.libsquaresloadingview.SquaresLoadingView
 * @date on 2018/9/19-21:31
 * @author fairytale110
 * @email fairytale110@foxmail.com
 * @description:
 */
package tech.nicesky.libsquaresloadingview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author fairytale110
 * @class tech.nicesky.libsquaresloadingview.SquaresLoadingView
 * @date on 2018/9/19-21:31
 * @email fairytale110@foxmail.com
 * @description: 规律缩放的九个方块，可控制动画速度，方块颜色，方块透明度，自动开始。
 */
public class SquaresLoadingView extends View {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private Handler handler;
    private Paint paintSquare;
    private ValueAnimator animator, animator1, animator2, animator3, animator4;
    private RectF[] rectFSquare = new RectF[9];

    private int paintAlpha = (int) (255F * 0.85F);

    /**
     * SquaresLoadingView's width default
     */
    private int widthViewDefault = 200;

    /**
     * SquaresLoadingView's width finally
     */
    private float widthView = 200F;

    /**
     * Squares's width finally
     */
    private float widthSquare = 200F;

    /**
     * Squares's scaled for anim
     */
    private float widthSquareScaled = 0F, widthSquareScaled1 = 0F, widthSquareScaled2 = 0F, widthSquareScaled3 = 0F, widthSquareScaled4 = 0F;
    private int mPaddingStart;
    private int mPaddingTop;

    /**
     * Squares's scale speed for user
     */
    private float speed = 0.5F;

    /**
     * Squares's scale speed for anim
     */
    private float speedToScale = speed * 2;

    /**
     * Squares's scaled anim looks like  paused
     */
    private float minScaled = -widthSquare / 2.5F;


    private int[] colorsDefault = {
            Color.parseColor("#C5523F"),
            Color.parseColor("#F2B736"),
            Color.parseColor("#499255"),
            Color.parseColor("#F2B736"),
            Color.parseColor("#499255"),
            Color.parseColor("#1875E5"),
            Color.parseColor("#499255"),
            Color.parseColor("#1875E5"),
            Color.parseColor("#C5523F"),
    };

    /**
     * 方块在放大
     */
    private boolean isIncrease = false, isIncrease1 = false, isIncrease2 = false, isIncrease3 = false, isIncrease4 = false;

    public SquaresLoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public SquaresLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SquaresLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SquaresLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context = context;
        this.handler = new Handler();
        this.paintSquare = new Paint();
        this.paintSquare.setColor(Color.RED);
        this.paintSquare.setAlpha(paintAlpha);
        this.paintSquare.setAntiAlias(true);
        this.paintSquare.setStyle(Paint.Style.FILL);

        for (int i = 0; i < rectFSquare.length; i++) {
            rectFSquare[i] = new RectF();
        }
        initAttr(context, attrs);
        anim();
    }

    /**
     * @param context see {@link Context}
     * @param attrs   see  {@link AttributeSet}
     */
    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SquaresLoadingView);

            int backgroundColor = attributes.getColor(R.styleable.SquaresLoadingView_slv_background, Color.parseColor("#FFFFFF"));
            speed = attributes.getFloat(R.styleable.SquaresLoadingView_slv_scale_speed, 0.5F);
            float wedgeAlpha = attributes.getFloat(R.styleable.SquaresLoadingView_slv_squares_alpha, 0.8F);

            setBackgroundColor(backgroundColor);
            setSquareAlpha(wedgeAlpha);
            setAnimSpeed(speed);
            attributes.recycle();
        }
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    /**
     * Initialization Animator
     */
    private void anim() {

        animator = ValueAnimator.ofInt(1, 500);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Log.w(TAG ,"anim widthSquareScaled " + widthSquareScaled);
                updateSquareRect();
                postInvalidate();
                if ((int) animation.getAnimatedValue() >= 120 && !animator1.isRunning()) {
                    animator1.start();
                }
                if ((int) animation.getAnimatedValue() >= 240 && !animator2.isRunning()) {
                    animator2.start();
                }
                if ((int) animation.getAnimatedValue() >= 360 && !animator3.isRunning()) {
                    animator3.start();
                }
                if ((int) animation.getAnimatedValue() >= 480 && !animator4.isRunning()) {
                    animator4.start();
                }
            }
        });

        animator1 = ValueAnimator.ofInt(0);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Log.w(TAG ,"anim widthSquareScaled " + widthSquareScaled);
                updateSquareRect1();
                postInvalidate();
            }
        });


        animator2 = ValueAnimator.ofInt(0);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Log.w(TAG ,"anim widthSquareScaled " + widthSquareScaled);
                updateSquareRect2();
                postInvalidate();
            }
        });

        animator3 = ValueAnimator.ofInt(0);
        animator3.setRepeatMode(ValueAnimator.REVERSE);
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Log.w(TAG ,"anim widthSquareScaled " + widthSquareScaled);
                updateSquareRect3();
                postInvalidate();
            }
        });


        animator4 = ValueAnimator.ofInt(0);
        animator4.setRepeatMode(ValueAnimator.REVERSE);
        animator4.setRepeatCount(ValueAnimator.INFINITE);
        animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Log.w(TAG ,"anim widthSquareScaled " + widthSquareScaled);
                updateSquareRect4();
                postInvalidate();
            }
        });
        ValueAnimatorUtil.resetDurationScale();
    }

    private void updateSquareRect() {
        if (isIncrease) {
            widthSquareScaled -= speedToScale ;
        } else  {
            widthSquareScaled += speedToScale;
        }

        if (widthSquareScaled <= minScaled) {
            isIncrease = false;
            widthSquareScaled = 0f;
        } else if (widthSquareScaled >= widthSquare / 2.5F) {
            isIncrease = true;
        }

        if (widthSquareScaled < 0) {
            //row 0, column 0
            rectFSquare[0].set(
                    mPaddingStart + 0F,
                    mPaddingTop + 0F,
                    mPaddingStart + widthSquare - 0F,
                    mPaddingTop + widthSquare - 0F);
        } else {

            //row 0, column 0
            rectFSquare[0].set(
                    mPaddingStart + widthSquareScaled,
                    mPaddingTop + widthSquareScaled,
                    mPaddingStart + widthSquare - widthSquareScaled,
                    mPaddingTop + widthSquare - widthSquareScaled);
        }
    }

    private void updateSquareRect1() {
        if (isIncrease1) {
            widthSquareScaled1 -= speedToScale;
        } else  {
            widthSquareScaled1 += speedToScale;
        }
        if (widthSquareScaled1 <= minScaled) {
            isIncrease1 = false;
            widthSquareScaled1 = 0f;
        } else if (widthSquareScaled1 >= widthSquare / 2.5F) {
            isIncrease1 = true;
        }

        if (widthSquareScaled1 < 0) {

            //row 0, column 1
            rectFSquare[1].set(
                    mPaddingStart + widthSquare,
                    mPaddingTop,
                    mPaddingStart + widthSquare * 2F,
                    mPaddingTop + widthSquare);
            //row 1, column 0
            rectFSquare[3].set(
                    mPaddingStart,
                    mPaddingTop + widthSquare,
                    mPaddingStart + widthSquare,
                    mPaddingTop + widthSquare * 2F);
        } else {
            //row 0, column 1
            rectFSquare[1].set(
                    mPaddingStart + widthSquare + widthSquareScaled1,
                    mPaddingTop + widthSquareScaled1,
                    mPaddingStart + widthSquare * 2F - widthSquareScaled1,
                    mPaddingTop + widthSquare - widthSquareScaled1);
            //row 1, column 0
            rectFSquare[3].set(
                    mPaddingStart + widthSquareScaled1,
                    mPaddingTop + widthSquare + widthSquareScaled1,
                    mPaddingStart + widthSquare - widthSquareScaled1,
                    mPaddingTop + widthSquare * 2F - widthSquareScaled1);

        }
    }

    private void updateSquareRect2() {
        if (isIncrease2) {
            widthSquareScaled2 -= speedToScale;
        } else {
            widthSquareScaled2 += speedToScale;
        }
        if (widthSquareScaled2 <= minScaled) {
            isIncrease2 = false;
            widthSquareScaled2 = 0f;
        } else if (widthSquareScaled2 >= widthSquare / 2.5F) {
            isIncrease2 = true;
        }

        if (widthSquareScaled2 < 0) {

            //row 0, column 2
            rectFSquare[2].set(
                    mPaddingStart + widthSquare * 2F,
                    mPaddingTop,
                    mPaddingStart + widthSquare * 3F,
                    mPaddingTop + widthSquare);


            //row 1, column 1
            rectFSquare[4].set(
                    mPaddingStart + widthSquare,
                    mPaddingTop + widthSquare,
                    mPaddingStart + widthSquare * 2F,
                    mPaddingTop + widthSquare * 2F);


            //row 2, column 0
            rectFSquare[6].set(
                    mPaddingStart,
                    mPaddingTop + widthSquare * 2F,
                    mPaddingStart + widthSquare,
                    mPaddingTop + widthSquare * 3F);
        } else {
            //row 0, column 2
            rectFSquare[2].set(
                    mPaddingStart + widthSquare * 2F + widthSquareScaled2,
                    mPaddingTop + widthSquareScaled2,
                    mPaddingStart + widthSquare * 3F - widthSquareScaled2,
                    mPaddingTop + widthSquare - widthSquareScaled2);

            //row 1, column 1
            rectFSquare[4].set(
                    mPaddingStart + widthSquare + widthSquareScaled2,
                    mPaddingTop + widthSquare + widthSquareScaled2,
                    mPaddingStart + widthSquare * 2F - widthSquareScaled2,
                    mPaddingTop + widthSquare * 2F - widthSquareScaled2);


            //row 2, column 0
            rectFSquare[6].set(
                    mPaddingStart + widthSquareScaled2,
                    mPaddingTop + widthSquare * 2F + widthSquareScaled2,
                    mPaddingStart + widthSquare - widthSquareScaled2,
                    mPaddingTop + widthSquare * 3F - widthSquareScaled2);
        }

    }

    private void updateSquareRect3() {
        if (isIncrease3) {
            widthSquareScaled3 -= speedToScale;
        } else {
            widthSquareScaled3 += speedToScale;
        }
        if (widthSquareScaled3 <= minScaled) {
            isIncrease3 = false;
            widthSquareScaled3 = 0f;
        } else if (widthSquareScaled3 >= widthSquare / 2.5F) {
            isIncrease3 = true;
        }

        if (widthSquareScaled3 < 0) {

            //row 1, column 2
            rectFSquare[5].set(
                    mPaddingStart + widthSquare * 2F,
                    mPaddingTop + widthSquare,
                    mPaddingStart + widthSquare * 3F,
                    mPaddingTop + widthSquare * 2F);

            //row 2, column 1
            rectFSquare[7].set(
                    mPaddingStart + widthSquare,
                    mPaddingTop + widthSquare * 2F,
                    mPaddingStart + widthSquare * 2F,
                    mPaddingTop + widthSquare * 3F);
        } else {
            //row 1, column 2
            rectFSquare[5].set(
                    mPaddingStart + widthSquare * 2F + widthSquareScaled3,
                    mPaddingTop + widthSquare + widthSquareScaled3,
                    mPaddingStart + widthSquare * 3F - widthSquareScaled3,
                    mPaddingTop + widthSquare * 2F - widthSquareScaled3);

            //row 2, column 1
            rectFSquare[7].set(
                    mPaddingStart + widthSquare + widthSquareScaled3,
                    mPaddingTop + widthSquare * 2F + widthSquareScaled3,
                    mPaddingStart + widthSquare * 2F - widthSquareScaled3,
                    mPaddingTop + widthSquare * 3F - widthSquareScaled3);
        }

    }

    private void updateSquareRect4() {
        if (isIncrease4) {
            widthSquareScaled4 -= speedToScale;
        } else {
            widthSquareScaled4 += speedToScale;
        }
        if (widthSquareScaled4 <= minScaled) {
            isIncrease4 = false;
            widthSquareScaled4 = 0F;
        } else if (widthSquareScaled4 >= widthSquare / 2.5F) {
            isIncrease4 = true;
        }
        if (widthSquareScaled4 < 0) {
            rectFSquare[8].set(
                    mPaddingStart + widthSquare * 2F,
                    mPaddingTop + widthSquare * 2F,
                    mPaddingStart + widthSquare * 3F,
                    mPaddingTop + widthSquare * 3F);
        } else {
            rectFSquare[8].set(
                    mPaddingStart + widthSquare * 2F + widthSquareScaled4,
                    mPaddingTop + widthSquare * 2F + widthSquareScaled4,
                    mPaddingStart + widthSquare * 3F - widthSquareScaled4,
                    mPaddingTop + widthSquare * 3F - widthSquareScaled4);
        }

    }

    private void stopAnim(ValueAnimator animator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMySize(widthViewDefault, widthMeasureSpec);
        int height = getMySize(widthViewDefault, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        widthView = width;
        widthSquare = widthView / 3F;

        rectFSquare[0].set(0F, 0F, widthSquare, widthSquare);
        rectFSquare[1].set(widthSquare, 0F, widthSquare * 2F, widthSquare);
        rectFSquare[2].set(widthSquare * 2F, 0F, widthSquare * 3F, widthSquare);
        rectFSquare[3].set(0F, widthSquare, widthSquare, widthSquare * 2F);
        rectFSquare[4].set(widthSquare, widthSquare, widthSquare * 2F, widthSquare * 2F);
        rectFSquare[5].set(widthSquare * 2F, widthSquare, widthSquare * 3F, widthSquare * 2F);
        rectFSquare[6].set(0F, widthSquare * 2F, widthSquare, widthSquare * 3F);
        rectFSquare[7].set(widthSquare, widthSquare * 2F, widthSquare * 2F, widthSquare * 3F);
        rectFSquare[8].set(widthSquare * 2F, widthSquare * 2F, widthSquare * 3F, widthSquare * 3F);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPaddingStart = getPaddingStart();
        int mPaddingEnd = getPaddingEnd();
        mPaddingTop = getPaddingTop();
        int mPaddingBottom = getPaddingBottom();

        float cH = widthView - mPaddingTop - mPaddingBottom;
        float cW = widthView - mPaddingStart - mPaddingEnd;
        widthSquare = (cW > cH ? cH : cW) / 3F;

//        Log.w(TAG, "widthSquare " + widthSquare + " widthView " + widthView);
//        Log.w(TAG, "mPaddingStart " + mPaddingStart + " mPaddingEnd " + mPaddingEnd + " mPaddingTop " + mPaddingTop + " mPaddingBottom " + mPaddingBottom);
        rectFSquare[0].set(mPaddingStart, mPaddingTop, mPaddingStart + widthSquare, mPaddingTop + widthSquare);
        rectFSquare[1].set(mPaddingStart + widthSquare, mPaddingTop, mPaddingStart + widthSquare * 2F, mPaddingTop + widthSquare);
        rectFSquare[2].set(mPaddingStart + widthSquare * 2F, mPaddingTop, mPaddingStart + widthSquare * 3F, mPaddingTop + widthSquare);
        rectFSquare[3].set(mPaddingStart, mPaddingTop + widthSquare, mPaddingStart + widthSquare, mPaddingTop + widthSquare * 2F);
        rectFSquare[4].set(mPaddingStart + widthSquare, mPaddingTop + widthSquare, mPaddingStart + widthSquare * 2F, mPaddingTop + widthSquare * 2F);
        rectFSquare[5].set(mPaddingStart + widthSquare * 2F, mPaddingTop + widthSquare, mPaddingStart + widthSquare * 3F, mPaddingTop + widthSquare * 2F);
        rectFSquare[6].set(mPaddingStart, mPaddingTop + widthSquare * 2F, mPaddingStart + widthSquare, mPaddingTop + widthSquare * 3F);
        rectFSquare[7].set(mPaddingStart + widthSquare, mPaddingTop + widthSquare * 2F, mPaddingStart + widthSquare * 2F, mPaddingTop + widthSquare * 3F);
        rectFSquare[8].set(mPaddingStart + widthSquare * 2F, mPaddingTop + widthSquare * 2F, mPaddingStart + widthSquare * 3F, mPaddingTop + widthSquare * 3F);

        speedToScale = speed * 1.5F * widthSquare / 50F;
        minScaled = -widthSquare / 2.5F;

        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < 9; i++) {
            drawSquare(canvas, i);
        }
    }

    private void drawSquare(Canvas canvas, int index) {
        paintSquare.setColor(colorsDefault[index]);
        canvas.drawRect(rectFSquare[index], paintSquare);
    }

    public void setSquareAlpha(float paintAlpha) {
        if (paintAlpha < 0.1F) {
            paintAlpha = 0.1F;
        }

        if (paintAlpha > 1.0F) {
            paintAlpha = 1.0F;
        }
        this.paintAlpha = (int) (255F * paintAlpha);
    }

    /**
     * Set rotate speed for wedges.
     *
     * @param scaleSpeed float value, must between 0.1F and 1.0F.
     */
    public void setAnimSpeed(float scaleSpeed) {
        if (scaleSpeed <= 0.1F) {
            scaleSpeed = 0.1F;
        } else if (scaleSpeed >= 1F) {
            scaleSpeed = 0.99F;
        }
        this.speed = scaleSpeed;
        speedToScale = speed * 1.5F * widthSquare / 50F;
    }


    public void setColors(int[] colors) {
        if (colors == null) {
            throw new IllegalArgumentException("colors cant not be null");
        }
        if (colors.length < 9) {
            throw new IllegalArgumentException("colors's length must be 9");
        }
        this.colorsDefault = colors;
    }

    /**
     * Reset and reStart the animation
     */
    public void start() {
        stop();
        if (animator != null)
            animator.start();
    }


    /**
     * Stop the animation
     */
    public void stop() {
        stopAnim(animator);
        stopAnim(animator1);
        stopAnim(animator2);
        stopAnim(animator3);
        stopAnim(animator4);
    }
}
