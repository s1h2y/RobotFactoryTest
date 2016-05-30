package cn.alpha.intell.factory.check.activitys;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import cn.alpha.intell.factory.check.R;

public class LCDActivity extends Activity {

    private final int mColors[] = {Color.RED, Color.GREEN, Color.BLUE, Color.WHITE, Color.BLACK};
    private final int SECOND_3 = 3000;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcd);
        switchImage();
//        mHandler.sendEmptyMessage(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switchImage();
        }
        return super.onTouchEvent(event);
    }

    void switchImage() {
        if (index < mColors.length) {
            getWindow().setBackgroundDrawable(new ColorDrawable(mColors[index++]));
        } else {
            finish();
        }
    }

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (index < mColors.length) {
//                switchImage();
//                sendEmptyMessageDelayed(2, SECOND_3);
//            } else {
//                finish();
//            }
//        }
//    };
}
