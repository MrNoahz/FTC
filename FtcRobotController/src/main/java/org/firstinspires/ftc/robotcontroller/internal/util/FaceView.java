package org.firstinspires.ftc.robotcontroller.internal.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;

import static android.R.attr.right;

public class FaceView extends View {
    private Paint drawPaint;
    private Canvas canvas;
    private Bitmap bitmap;
    private DisplayMetrics metrics = new DisplayMetrics();

    private int width;
    private int height;
    private float dwidth;
    private float dheight;
    private float density;
    private Rect dimen;

    private Eye leftEye;
    private Eye rightEye;

    private MediaPlayer helloSound;

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    private void setupPaint() {
        helloSound = MediaPlayer.create((Activity) this.getContext(), R.raw.hello);

        ((Activity) this.getContext()).getWindowManager()
                                      .getDefaultDisplay()
                                      .getMetrics(metrics);
        height  = metrics.heightPixels + getStatusBarHeight();
        width   = metrics.widthPixels;
        density = metrics.density;
        dwidth  = width / density;
        dheight = height / density;

        dimen = new Rect(0, 0, width, height);

        bitmap = Bitmap.createBitmap(width,
                                     height,
                                     Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);

        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawColor(Color.BLACK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            leftEye  = new Eye(0, 0, 90, 90, 20);
            rightEye = new Eye(0, 0, 90, 90, 20);

            setExpression(Expression.NEUTRAL);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, null, dimen, null);
    }

    public void setExpression(Expression express) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        switch(express) {
            case NEUTRAL:
                leftEye.setDimen(90, 90);
                leftEye.setCoords((int)(dwidth * 0.375) - 10,
                        (int) dheight / 4);

                rightEye.setDimen(90, 90);
                rightEye.setCoords((int)(dwidth * 0.625) + 10,
                        (int) dheight / 4);

                leftEye.setRadius(20);
                rightEye.setRadius(20);

                leftEye.draw(canvas);
                rightEye.draw(canvas);

                invalidate();

//                new android.os.Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       setExpression(Expression.CURIOUS_LEFT);
//                    }
//                }, 2000);
                break;
            case CURIOUS_LEFT:

                leftEye.setDimen(90, 90);
                leftEye.setCoords((int)(dwidth * 0.375) - 10 - 50,
                        (int) dheight / 4);

                rightEye.setDimen(90, 70);
                rightEye.setCoords((int)(dwidth * 0.625) + 10 - 50,
                        (int) dheight / 4 + 10);

                leftEye.setRadius(20);
                rightEye.setRadius(20);

                leftEye.draw(canvas);
                rightEye.draw(canvas);
//                helloSound.start();

                invalidate();

//                new android.os.Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setExpression(Expression.CURIOUS_RIGHT);
//                    }
//                }, 2000);
                break;
            case CURIOUS_RIGHT:
                leftEye.setDimen(90, 70);
                leftEye.setCoords((int)(dwidth * 0.375) - 10 + 50,
                        (int) dheight / 4 + 10);

                rightEye.setDimen(90, 90);
                rightEye.setCoords((int)(dwidth * 0.625) + 10 + 50,
                        (int) dheight / 4);

                leftEye.setRadius(20);
                rightEye.setRadius(20);

                leftEye.draw(canvas);
                rightEye.draw(canvas);

//                helloSound.start();

                invalidate();
                break;
            case HAPPY:
                leftEye.setDimen(90, 90);
                leftEye.setCoords((int)(dwidth * 0.375) - 10,
                        (int) dheight / 4);
                leftEye.setRadius(40);

                rightEye.setDimen(90, 90);
                rightEye.setCoords((int)(dwidth * 0.625) + 10,
                        (int) dheight / 4);
                rightEye.setRadius(40);

                leftEye.draw(canvas);
                rightEye.draw(canvas);

                drawPaint.setColor(Color.BLACK);
                drawPaint.setStyle(Paint.Style.FILL);

                canvas.drawCircle(((float) (dwidth * 0.375) - 10) * density, (dheight / 4 + 50) * density, 150, drawPaint);
                canvas.drawCircle(((float) (dwidth * 0.625) + 10) * density, (dheight / 4 + 50) * density, 150, drawPaint);

                invalidate();

                break;
            case MAD:
                leftEye.setDimen(90, 90);
                leftEye.setCoords((int)(dwidth * 0.375) - 10,
                        (int) dheight / 4);
                leftEye.setRadius(5);

                rightEye.setDimen(90, 90);
                rightEye.setCoords((int)(dwidth * 0.625) + 10,
                        (int) dheight / 4);
                rightEye.setRadius(5);

                leftEye.draw(canvas);
                rightEye.draw(canvas);

                drawPaint.setColor(Color.BLACK);
                Point a1 = new Point((int)(((dwidth * 0.375) - 10 - 45) * density), (int) ((dheight / 4 - 45) * density));
                Point b1 = new Point((int)(((dwidth * 0.375) - 10 + 45) * density), (int) ((dheight / 4 - 45) * density));
                Point c1 = new Point((int)(((dwidth * 0.375) - 10 + 45) * density), (int) ((dheight / 4 + 0) * density));
                Point d1 = new Point((int)(((dwidth * 0.375) - 10 - 45) * density), (int) ((dheight / 4 - 25) * density));

                Point a2 = new Point((int)(((dwidth * 0.675) - 8 + 45) * density), (int) ((dheight / 4 - 45) * density));
                Point b2 = new Point((int)(((dwidth * 0.675) - 8 - 45) * density), (int) ((dheight / 4 - 45) * density));
                Point c2 = new Point((int)(((dwidth * 0.675) - 8 - 45) * density), (int) ((dheight / 4 + 0) * density));
                Point d2 = new Point((int)(((dwidth * 0.675) - 8 + 45) * density), (int) ((dheight / 4 - 25) * density));

                Path path1 = new Path();
                path1.setFillType(Path.FillType.EVEN_ODD);
                path1.moveTo(a1.x, a1.y);
                path1.lineTo(b1.x, b1.y);
                path1.lineTo(c1.x, c1.y);
                path1.lineTo(d1.x, d1.y);
                path1.lineTo(a1.x, a1.y);
                path1.close();

                Path path2 = new Path();
                path2.setFillType(Path.FillType.EVEN_ODD);
                path2.moveTo(a2.x, a2.y);
                path2.lineTo(b2.x, b2.y);
                path2.lineTo(c2.x, c2.y);
                path2.lineTo(d2.x, d2.y);
                path2.lineTo(a2.x, a2.y);
                path2.close();

                canvas.drawPath(path1, drawPaint);
                canvas.drawPath(path2, drawPaint);

                invalidate();
                break;
            case SAD:

                break;
        }
    }

    class Eye {
        private int x;
        private int y;

        private int width;
        private int height;

        private int radius;

        private RectF rect;

        public Eye(int x, int y, int width, int height, int radius) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.radius = radius;

            rect = new RectF((x - (width / 2)) * density,
                             (y - (height / 2)) * density,
                             (x + (int)(width * .5)) * density,
                             (y + (int)(height * .5)) * density);
        }

        public void draw(Canvas c) {
            drawPaint.setStyle(Paint.Style.FILL);
            drawPaint.setColor(Color.parseColor("#33b5e5"));

            c.drawRoundRect(rect,
                            radius * density,
                            radius * density,
                            drawPaint);
        }

        public void setX(int i) {
            x = i;

            rect.set((x - (width / 2)) * density,
                     (y - (height / 2)) * density,
                     (x + (int)(width * .5)) * density,
                     (y + (int)(height * .5)) * density);
        }

        public void setY(int i) {
            y = i;

            rect.set((x - (width / 2)) * density,
                     (y - (height / 2)) * density,
                     (x + (int)(width * .5)) * density,
                     (y + (int)(height * .5)) * density);
        }

        public void setCoords(int x, int y) {
            this.x = x;
            this.y = y;

            rect.set((x - (width / 2)) * density,
                     (y - (height / 2)) * density,
                     (x + (int)(width * .5)) * density,
                     (y + (int)(height * .5)) * density);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setWidth(int i) {
            width = i;

            rect.set((x - (width / 2)) * density,
                    (y - (height / 2)) * density,
                    (x + (int)(width * .5)) * density,
                    (y + (int)(height * .5)) * density);
        }

        public void setHeight(int i) {
            height = i;

            rect.set((x - (width / 2)) * density,
                    (y - (height / 2)) * density,
                    (x + (int)(width * .5)) * density,
                    (y + (int)(height * .5)) * density);
        }

        public void setDimen(int w, int h) {
            width = w;
            height = h;

            rect.set((x - (width / 2)) * density,
                    (y - (height / 2)) * density,
                    (x + (int)(width * .5)) * density,
                    (y + (int)(height * .5)) * density);
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void setRadius(int i) {
            radius = i;

            rect.set((x - (width / 2)) * density,
                    (y - (height / 2)) * density,
                    (x + (int)(width * .5)) * density,
                    (y + (int)(height * .5)) * density);
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result * 2;
    }
}