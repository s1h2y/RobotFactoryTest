package cn.alpha.intell.factory.check.activitys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import cn.alpha.intell.factory.check.R;

public class TouchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawView view = new DrawView(this);
        //setContentView(R.layout.activity_touch);
        setContentView(view);
    }

    class DrawView extends View {

        private Paint mPaint;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private int mXmov;
        private int mYmov;
        public DrawView(Context context) {
            super(context);
            setBackgroundResource(R.drawable.touch);
            mPaint = new Paint(Paint.DITHER_FLAG);
            mBitmap = Bitmap.createBitmap(1280, 720, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas();
            mCanvas.setBitmap(mBitmap);            Log.d("shy", "DrawView");
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(25);
            mPaint.setColor(Color.RED);
            mPaint.setAntiAlias(true);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mCanvas.drawLine(mXmov, mYmov, event.getX(), event.getY(), mPaint);
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mXmov = (int)event.getX();
                mYmov = (int)event.getY();
                mCanvas.drawPoint(mXmov, mYmov, mPaint);
                invalidate();
            }
            mXmov = (int)event.getX();
            mYmov = (int)event.getY();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //super.onDraw(canvas);
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }
}
