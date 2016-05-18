package com.alpha.smart.factorytest.activitys;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;

public class HeadLightActivity extends Activity {
    private final int mColors[] = {Color.parseColor("#4FA5F1"), Color.parseColor("#F97664"), Color.parseColor("#2CBA16")};
    private final int mTexts[] = {R.string.origin_color, R.string.red_color, R.string.green_color};
    private final int SECOND_3 = 3000;
    private int index = 0;
    private TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_light);
        mText = (TextView)findViewById(R.id.tips);
        mHandler.sendEmptyMessage(0);
    }
    void switchLight() {
        //TODO turn on lights
        mText.setText(mTexts[index]);
        mText.setTextColor(mColors[index]);
        index++;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (index < mColors.length) {
                switchLight();
                sendEmptyMessageDelayed(2, SECOND_3);
            } else {
                finish();
            }
        }
    };
}
