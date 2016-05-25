package com.alpha.smart.factorytest.activitys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.beans.ResultBean;
import com.alpha.smart.factorytest.utils.Constant;
import com.alpha.smart.factorytest.utils.Result;

public class ResultActivity extends Activity {

    final int TYPE_TITLE = 0;
    final int TYPE_ITEM = 1;
    ResultBean mRes[] = {
            new ResultBean(Constant.LCD, TYPE_TITLE, R.string.list_lcd, true),
            new ResultBean(Constant.LCD, TYPE_ITEM, R.string.dead_dot, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_button, true),
            new ResultBean(Constant.BUTTON_VOL_UP, TYPE_ITEM, R.string.vol_up, true),
            new ResultBean(Constant.BUTTON_VOL_DOWN, TYPE_ITEM, R.string.vol_down, true),
            new ResultBean(Constant.BUTTON_REC, TYPE_ITEM, R.string.rec, true),
            new ResultBean(Constant.BUTTON_BACK, TYPE_ITEM, R.string.back, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_lightness, true),
            new ResultBean(Constant.LIGHTNESS, TYPE_ITEM, R.string.lightness, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_touch, true),
            new ResultBean(Constant.TOUCH, TYPE_ITEM, R.string.touch, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_speaker, true),
            new ResultBean(Constant.SPEAKER_NO_SOUND, TYPE_ITEM, R.string.nosound, true),
            new ResultBean(Constant.SPEAKER_BOOM, TYPE_ITEM, R.string.sound_boom, true),
            new ResultBean(Constant.SPEAKER_NO_CHANGE, TYPE_ITEM, R.string.sound_no_change, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_mic, true),
            new ResultBean(Constant.MIC, TYPE_ITEM, R.string.mic, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_standby, true),
            new ResultBean(Constant.STANDBY, TYPE_ITEM, R.string.standby, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_wifi, true),
            new ResultBean(Constant.WIFI_SEARCH, TYPE_ITEM, R.string.wifisearch, true),
            new ResultBean(Constant.WIFI_CONNECT, TYPE_ITEM, R.string.wificonnect, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_camera, true),
            new ResultBean(Constant.CAMERA_SHOT, TYPE_ITEM, R.string.camerashot, true),
            new ResultBean(Constant.CAMERA_FOCUS, TYPE_ITEM, R.string.camerafocus, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_head_light, true),
            new ResultBean(Constant.HEAD_LIGHT_ORIGIN, TYPE_ITEM, R.string.origin_color, true),
            new ResultBean(Constant.HEAD_LIGHT_RED, TYPE_ITEM, R.string.red_color, true),
            new ResultBean(Constant.HEAD_LIGHT_GREEN, TYPE_ITEM, R.string.green_color, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_head_shake, true),
            new ResultBean(Constant.HEAD_SHAKE, TYPE_ITEM, R.string.shake_up_down, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_body_shake, true),
            new ResultBean(Constant.BODY_SHAKE, TYPE_ITEM, R.string.shake_left_right, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_hand, true),
            new ResultBean(Constant.HAND_LEFT, TYPE_ITEM, R.string.left, true),
            new ResultBean(Constant.HAND_RIGHT, TYPE_ITEM, R.string.right, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_gsensor, true),
            new ResultBean(Constant.GSENSOR_X, TYPE_ITEM, R.string.x_cord, true),
            new ResultBean(Constant.GSENSOR_Y, TYPE_ITEM, R.string.y_cord, true),
            new ResultBean(Constant.GSENSOR_Z, TYPE_ITEM, R.string.z_cord, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_rtc, true),
            new ResultBean(Constant.RTC, TYPE_ITEM, R.string.time, true),
//            new ResultBean(null, TYPE_TITLE, R.string.list_ddr, true),
//            new ResultBean(Constant.DDR, TYPE_ITEM, R.string.ddr, true),
            new ResultBean(null, TYPE_TITLE, R.string.list_monitor_light, true),
            new ResultBean(Constant.MONITOR, TYPE_ITEM, R.string.monitor, true),
            new ResultBean(null, TYPE_TITLE, R.string.speech, true),
            new ResultBean(Constant.SPEECH, TYPE_ITEM, R.string.speech, true),
    };
    private Context mContext;
    private MyAdapter myAdapter;
    private TextView mResText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mContext = this;
        prepareData();
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(myAdapter = new MyAdapter());
        mResText = (TextView)findViewById(R.id.result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkResult();
    }

    private void prepareData() {
        for (int i = 0; i < mRes.length; i++) {
            if (Constant.FAILED.equals(Result.get(mRes[i].key))) {
                mRes[i].status = false;
            }
        }
    }

    private void checkResult() {
        if (Result.isOK(this)) {
            showResultOK();
        } else {
            showResultFail();
        }
    }

    private void showResultFail() {
        mResText.setText(R.string.result_test_fail);
        mResText.setTextColor(Color.RED);
    }

    private void showResultOK() {
        mResText.setText(R.string.main_test_pass);
        mResText.setTextColor(Color.GREEN);
    }

    class MyAdapter extends BaseAdapter {

        LayoutInflater inflater;

        @Override
        public int getCount() {
            return mRes.length;
        }

        @Override
        public Object getItem(int position) {
            return mRes[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TitleViewHolder titleHolder = null;
            ItemViewHolder itemHolder = null;
            int type = getItemViewType(position);
            if (null == convertView) {
                inflater = LayoutInflater.from(mContext);
                switch (type) {
                    case TYPE_TITLE:
                        convertView = inflater.inflate(R.layout.result_title, null);
                        titleHolder = new TitleViewHolder();
                        titleHolder.title = (TextView) convertView.findViewById(R.id.title);
                        convertView.setTag(titleHolder);
                        break;
                    case TYPE_ITEM:
                        convertView = inflater.inflate(R.layout.result_item, null);
                        itemHolder = new ItemViewHolder();
                        itemHolder.key = (TextView) convertView.findViewById(R.id.key);
                        itemHolder.val = (TextView) convertView.findViewById(R.id.val);
                        convertView.setTag(itemHolder);
                        break;
                }
            } else {
                switch (type) {
                    case TYPE_TITLE:
                        titleHolder = (TitleViewHolder) convertView.getTag();
                        break;
                    case TYPE_ITEM:
                        itemHolder = (ItemViewHolder) convertView.getTag();
                        break;
                }
            }
            switch (type) {
                case TYPE_TITLE:
                    titleHolder.title.setText(mRes[position].value);
                    break;
                case TYPE_ITEM:
                    itemHolder.key.setText(mRes[position].value);
                    if (mRes[position].status) {
                        itemHolder.val.setText(R.string.function_ok);
                        itemHolder.val.setTextColor(Color.parseColor("#2CBA16"));
                    } else {
                        itemHolder.val.setText(R.string.function_fail);
                        itemHolder.val.setTextColor(Color.RED);
                    }
                    break;
            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return mRes[position].type;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        class ItemViewHolder {
            TextView key;
            TextView val;
        }

        class TitleViewHolder {
            TextView title;
        }
    }
}
